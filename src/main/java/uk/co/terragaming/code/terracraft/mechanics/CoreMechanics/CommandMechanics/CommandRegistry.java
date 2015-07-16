package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.TagArg;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.commands.CommandHelp;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandRegistry {
	
	private final static HashMap<String, Command> rootCommands = new HashMap<String, Command>();
	private final static CommandMap commandMap = CommandMechanics.getInstance().getCommandMap();
	
	// Register All Commands from a Class
	
	public static void registerCommands(JavaPlugin plugin, Object handler) {
		TerraLogger.blank();
		TerraLogger.info("Registering Commands in '" + handler.getClass().getSimpleName() + "'");
		TerraLogger.blank();
		
		// Get the methods in the class ...
		List<Method> methods = CommandMethod.getMethodsInClass(handler);
		
		// ... and For each method in the handler class ...
		for (Method method : methods) {
			try {
				registerMethod(plugin, handler, method);
			} catch (TerraException e) {
				break;
			}
		}
		
		TerraLogger.blank();
	}
	
	// Register A Command from a Method
	
	public static void registerMethod(JavaPlugin plugin, Object handler, Method method) throws TerraException {
		
		// If the method is a commandMethod ...
		if (!CommandMethod.isCommand(method))
			return;
		
		String name = CommandMethod.getCommandName(method);
		
		List<String> aliases = CommandMethod.getCommandAliases(method);
		String description = "";
		String usage = null;
		Optional<Command> parent = Optional.empty();
		
		if (CommandMethod.hasDescription(method)) {
			description = CommandMethod.getDescription(method);
		}
		
		if (CommandMethod.hasUsage(method)) {
			usage = CommandMethod.getUsage(method);
		}
		
		if (CommandMethod.hasParent(method)) {
			parent = CommandMethod.getParent(method);
			if (!parent.isPresent()) {
				TerraLogger.error("Command Registered Before Parent: " + method.getName() + " [" + CommandMethod.getParentString(method) + "]");
				throw new TerraException();
			}
		}
		
		try {
			CommandParameter[] params = getParams(method);
			
			Optional<Command> ret = createCommand(plugin, handler, method, name, aliases, description, usage, parent, params);
			
			if (ret.isPresent()) {
				registerCommand(ret.get(), handler, method);
				if (method.isAnnotationPresent(HelpCommand.class)) {
					CommandHelp.createHelpCommand(plugin, ret.get());
				}
			}
			
		} catch (TerraException e) {
			for (String string : Txt.wrap(e.getMessages())) {
				TerraLogger.error("[<l>TerraCraft<r>] " + string);
			}
			return;
		}
	}
	
	// Create the Command
	
	public static Optional<Command> createCommand(JavaPlugin plugin, Object handler, Method method, String commandName, List<String> commandAliases, String commandDescription, String commandUsage, Optional<Command> parent, CommandParameter[] params) {
		Command command;
		
		if (!parent.isPresent()) {
			command = createRootCommand(plugin, commandName, method.getParameterCount());
		} else {
			command = createChildCommand(handler, parent.get(), commandName, method.getParameterCount());
		}
		
		int i = 0;
		for (CommandParameter param : params) {
			command.addParam(param, i);
			i++;
		}
		
		command.setDescription(commandDescription);
		if (commandAliases.size() > 0) {
			command.setAliases(commandAliases);
		}
		if (commandUsage == null) {
			command.setUsage(generateUsage(command));
		} else {
			command.setUsage(commandUsage);
		}
		
		return Optional.of(command);
	}
	
	// Create a Root Command
	
	private static Command createRootCommand(JavaPlugin plugin, String commandName, Integer paramCount) {
		BukkitCommand bukkitCommand = new BukkitCommand(commandName);
		bukkitCommand.setTabExecutor(new CommandExecutor());
		
		CommandRoot root = new CommandRoot(bukkitCommand, paramCount);
		
		return root;
	}
	
	// Create a Child Command
	
	private static Command createChildCommand(Object handler, Command parent, String commandName, Integer paramCount) {
		CommandChild child = new CommandChild(commandName, paramCount);
		
		child.setParent(parent);
		parent.addChild(child);
		
		return child;
	}
	
	// Get the CommandParameters from the Method
	
	public static CommandParameter[] getParams(Method method) throws TerraException {
		CommandParameter[] ret = new CommandParameter[method.getParameterCount()];
		Parameter[] params = method.getParameters();
		
		Integer paramIndex = 0;
		for (Parameter param : params) {
			CommandParameter parameter = new CommandParameter();
			
			if (param.isNamePresent()) {
				parameter.setName(param.getName());
			} else {
				TerraLogger.error("Not Using Java 8...");
				throw new TerraException("<b>Name Not Present for Paramter");
			}
		
			if (paramIndex < 3 && (param.getType().equals(CommandSender.class) || param.getType().equals(Player.class) || param.getType().equals(ConsoleCommandSender.class))) {
				parameter.setSpecial(true);
				parameter.setName("sender");
			}
			
			if (paramIndex < 3 && param.getType().equals(Command.class)) {
				parameter.setSpecial(true);
				parameter.setName("command");
			}
			
			if (paramIndex < 3 && param.getType().equals(Language.class)) {
				parameter.setSpecial(true);
				parameter.setName("language");
			}
			
			parameter.setType(param.getType());
			
			parameter.setOptional(param.isAnnotationPresent(OptArg.class));
			parameter.setTag(param.isAnnotationPresent(TagArg.class));
			
			if (parameter.isOptional()) {
				parameter.setDefaultValue(param.getAnnotation(OptArg.class).value());
			}
			
			ret[paramIndex] = parameter;
			
			paramIndex++;
		}
		
		return ret;
	}
	
	// Generate Command Usage from Parameters
	
	private static String generateUsage(Command command) {
		String usage = "<c>/" + command.getPath() + "<p>";
		
		for (CommandParameter param : command.getParams()) {
			if (param.isSpecial()) {
				continue;
			}
			
			if (param.isTag()) {
				usage += " [-" + param.getName() + "]";
			} else if (param.isOptional()) {
				usage += " [" + param.getName() + "]";
			} else {
				usage += " <" + param.getName() + ">";
			}
		}
		return usage;
	}
	
	// Registering Command in commandMap and Registry
	
	public static void registerCommand(Command command, Object handler, Method method) {
		command.setHandler(handler);
		command.setMethod(method);
		
		if (command instanceof CommandRoot) {
			CommandRoot commandRoot = (CommandRoot) command;
			commandMap.register(command.getName(), "TerraCraft", commandRoot.command);
			rootCommands.put(command.getPath(), command);
		}
		
		TerraLogger.info("Registered Command: '/" + command.getPath() + "'");
	}
	
	// Get Command from Path
	
	public static Command getCommand(String[] args) {
		if (args.length < 1)
			return null;
		Command rootCommand = getRootCommand(args[0]);
		if (args.length == 1)
			return rootCommand;
		
		return getChildCommand(rootCommand, Arrays.copyOfRange(args, 1, args.length));
	}
	
	// Get Root Command from BukkitCommand or CommandLabel
	
	public static Command getRootCommand(org.bukkit.command.Command bukkitCommand) {
		return rootCommands.get(bukkitCommand.getName());
	}
	
	public static Command getRootCommand(String commandLabel) {
		return rootCommands.get(commandLabel);
	}
	
	// Get Child Command from Arguments
	
	public static Command getChildCommand(Command command, String[] args) {
		return getChildCommandWithArgs(command, args).getKey();
	}
	
	public static Entry<Command, List<String>> getChildCommandWithArgs(Command command, String[] args) {
		return getChildCommandWithArgs(command, Lists.newArrayList(args));
	}
	
	private static Entry<Command, List<String>> getChildCommandWithArgs(Command parent, List<String> args) {
		boolean check = false;
		
		if (parent.getChildren().isEmpty()) {
			check = true;
		}
		if (args.isEmpty()) {
			check = true;
		}
		
		if (check) {
			Entry<Command, List<String>> result = new AbstractMap.SimpleEntry<Command, List<String>>(parent, args);
			return result;
		}
		
		Command child = getChildCommand(args.get(0), parent);
		
		if (child == null) {
			Entry<Command, List<String>> result = new AbstractMap.SimpleEntry<Command, List<String>>(parent, args);
			return result;
		}
		
		args.remove(0);
		return getChildCommandWithArgs(child, args);
	}
	
	private static Command getChildCommand(String arg, Command parent) {
		if (parent == null | arg == null)
			return null;
		
		for (Command subCommand : parent.getChildren()) {
			if (subCommand.getName().equalsIgnoreCase(arg))
				return subCommand;
			for (String subAlias : subCommand.getAliases()) {
				if (subAlias.equalsIgnoreCase(arg))
					return subCommand;
			}
		}
		return null;
	}
}
