package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.AR;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandHandler implements CommandExecutor{

	private final static HashMap<String, CommandAbstract> commands = new HashMap<String, CommandAbstract>(); // Including parents recursivly, Used for checking parents in registration
	private final static HashMap<String, CommandAbstract> rootCommands = new HashMap<String, CommandAbstract>(); // Includes aliases
	
	public static void registerCommands(JavaPlugin plugin, Object handler){
		// Get the methods in the class ...
		List<Method> methods = getMethodsInClass(handler);
		
		// For each method in handler class ...
		for (Iterator<Method> iter = methods.iterator(); iter.hasNext();){
			Method method = iter.next();
			Optional<CommandAbstract> command = registerMethod(plugin, handler, method);
			if(!command.isPresent()){ continue; }
		}
	}
	
	private static List<Method> getMethodsInClass(Object handler){
		List<Method> methods = Lists.newArrayList(handler.getClass().getMethods());
		Collections.sort(methods, new Comparator<Method>() {
			@Override
			public int compare(final Method m1, final Method m2) {
				if (!m1.isAnnotationPresent(Command.class)){ return 0; }
				if (!m2.isAnnotationPresent(Command.class)){ return 0; }
				
				if (!m1.isAnnotationPresent(CommandParent.class)){ return -1; }
				if (!m2.isAnnotationPresent(CommandParent.class)){ return 1; }

				Integer m1l = m1.getAnnotation(CommandParent.class).value().split(" ").length;
				Integer m2l = m1.getAnnotation(CommandParent.class).value().split(" ").length;
				
				if (m1l > m2l){
					return 1;
				} else if (m2l < m1l){
					return -1;
				} else {
					return 0;
				}
			}
		});
		
		return methods;
	}
	
	private static Optional<CommandAbstract> registerMethod(JavaPlugin plugin, Object handler, Method method){
		if (!method.isAnnotationPresent(Command.class)){ return Optional.empty(); }
		Command commandAnnotation = method.getAnnotation(Command.class);
		
		String name = "";
		List<String> aliases = Lists.newArrayList();
		String description = "";
		String usage = "";
		Optional<CommandAbstract> parent = Optional.empty();
		
		name = commandAnnotation.value()[0];
		aliases = Lists.newArrayList(commandAnnotation.value());
		aliases.remove(0);
		
		if (method.isAnnotationPresent(CommandDescription.class)){
			CommandDescription commandDescription = method.getAnnotation(CommandDescription.class);
			description = commandDescription.value();
		}

		if (method.isAnnotationPresent(CommandUsage.class)){
			CommandUsage commandUsage = method.getAnnotation(CommandUsage.class);
			usage = commandUsage.value();
		}
		
		if (method.isAnnotationPresent(CommandParent.class)){
			CommandParent commandParent = method.getAnnotation(CommandParent.class);
			String parentName = commandParent.value();
			
			if (!commands.containsKey(parentName)){ TerraLogger.error("Command Registered Before Parent: " + method.getName() + " [" + parentName + "]"); return Optional.empty(); }
			parent = Optional.of(commands.get(parentName));
		}
		
		Optional<CommandAbstract> ret = createCommand(plugin, handler, method, name, aliases, description, usage, parent);
		if (ret.isPresent()){
			if (method.isAnnotationPresent(HelpCommand.class)){
				createHelpCommand(plugin, ret.get());
			}
		}
		return ret;
	}
	
	private static Optional<CommandAbstract> createCommand(JavaPlugin plugin, Object handler, Method method, String commandName, List<String> commandAliases, String commandDescription, String commandUsage, Optional<CommandAbstract> parent){
		Optional<CommandAbstract> optionalCommand;
		
		if (!parent.isPresent()){
			optionalCommand = createRootCommand(plugin, commandName, commandAliases);
		} else {
			optionalCommand = createSubCommand(handler, parent.get(), commandName, commandAliases);
		}
		
		if (!optionalCommand.isPresent()){ return Optional.empty(); }
		CommandAbstract command = optionalCommand.get();
		
		command.setHandler(handler);
		command.setMethod(method);
		command.setDescription(commandDescription);
		command.setUsage(commandUsage);
		
		commands.put(command.getPath(), command);
		if (!parent.isPresent()){
			rootCommands.put(command.getPath(), command);
		}
		
		TerraLogger.info("Registered Command: '" + command.getPath() + "'");
		
		return Optional.of(command);
	}

	private static Optional<CommandAbstract> createRootCommand(JavaPlugin plugin, String commandName, List<String> commandAliases) {
		if (plugin.getCommand(commandName) == null){ TerraLogger.error("Could not register command " + commandName + " because it was not set in plugin.yml"); return Optional.empty(); }
		PluginCommand pc = plugin.getCommand(commandName);
		
		// ... set the command aliases and executor ...
		if (commandAliases.size() > 0){ pc.setAliases(commandAliases); }
		pc.setExecutor(new CommandHandler());
		
		// ... and create a root command.
		CommandRoot root = new CommandRoot(pc);
		return Optional.of(root);
	}
	
	private static Optional<CommandAbstract> createSubCommand(Object handler, CommandAbstract parent, String commandName, List<String> commandAliases) {
		CommandSub child = new CommandSub(commandName);
		
		if (commandAliases.size() > 0){ child.setAliases(commandAliases); }
		
		child.setParent(parent);
		parent.addSubCommand(child);

		return Optional.of((CommandAbstract) child);
	}

	private static void createHelpCommand(JavaPlugin plugin, CommandAbstract parent){
		try {
			createCommand(plugin, new CommandHelp(), CommandHelp.class.getMethod("onHelpCommand", CommandSender.class, CommandAbstract.class, Integer.class), "help", Lists.newArrayList(), "Help for " + parent.getName(), "/" + parent.getPath() + " help", Optional.of(parent));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	
	private CommandAbstract getSubCommand(String arg, CommandAbstract parent){
		if (parent == null || arg == null) return null;
		
		for (CommandAbstract subCommand : parent.getSubCommands()){
			if (subCommand.getName().equalsIgnoreCase(arg)){
				return subCommand;
			}
			for (String subAlias : subCommand.getAliases()){
				if (subAlias.equalsIgnoreCase(arg)){
					return subCommand;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private CommandAbstract getSubCommand(String[] args, CommandAbstract cmd){
		for(int i = 0; i < args.length-1; i++){
			String arg = args[i];
			if (cmd == null) break;
			
			cmd = this.getSubCommand(arg, cmd);
		}
		
		return cmd;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] parsedArgs) {
		CommandAbstract command = rootCommands.get(bukkitCommand.getName());;
		List<String> args;
		
		// If the command has at least 1 arg [possible subcommand] ...
		if (parsedArgs.length > 0){
			Entry<CommandAbstract, List<String>> result = getSubCommandMap(command, Lists.newArrayList(parsedArgs));
			
			command = result.getKey();
			args = result.getValue();
		} else {
			args = Lists.newArrayList(parsedArgs);
		}
		
		List<String> formatedArgs = Lists.newArrayList();
		
		for (int i=0; i<args.size(); i++){
			formatedArgs.add(i, "<p>" + args.get(i));
		}
		sender.sendMessage("Command Path: " + command.getPath());
		sender.sendMessage("Command Args: " + Txt.parse(Txt.implodeCommaAnd(formatedArgs, "<r>")));
		
		Object handler = command.getHandler();
		Method method = command.getMethod();
		
		if (handler == null | method == null){ sender.sendMessage("Unknown command. Type \"help\" for help."); return true; }
		
		if(method.getParameterTypes()[0].equals(Player.class) && !(sender instanceof Player)){
			sender.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" + ChatColor.WHITE + "] This command can only be run as a player.");
			return true;
		}
		
		if(method.getParameterTypes()[0].equals(ConsoleCommandSender.class) && !(sender instanceof ConsoleCommandSender)){
			sender.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" + ChatColor.WHITE + "] This command can only be run from the console.");
			return true;
		}
		
		Parameter[] parameters = method.getParameters();
		Object[] arguments = new Object[parameters.length];
		int i = 0;
		for (Parameter parameter : parameters){
			String name = parameter.getName();
			Class<?> type = parameter.getType();
			
			String defaultValue = null;
			
			if (parameter.isAnnotationPresent(OptArg.class)){
				OptArg optArg = parameter.getAnnotation(OptArg.class);
				defaultValue = optArg.value();
			}
			
			TerraLogger.debug("Parameter: " + name + " [" + type.getSimpleName() + "] " + (!parameter.isAnnotationPresent(OptArg.class) ? "" : "@OptArg(" + defaultValue + ")"));
		
			if (name.equals("sender")){arguments[i] = sender; continue;}
			if (name.equals("command")){arguments[i] = command; continue;}
			
			try {
			
				Class<?> cl = Class.forName("uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.AR" + Txt.upperCaseFirst(type.getSimpleName()));
				
				Object c = cl.newInstance();
				AR<?> ar = (AR<?>) c;
				
				Object arg = null;
				
				if (i < args.size()){
					
					arg = ar.read(args.get(i), sender);
				}
				if (arg == null && defaultValue != null){
					arg = ar.read(defaultValue, sender);
				}
				TerraLogger.debug(arg.toString());
				arguments[i] = arg;
				
			} catch (ClassNotFoundException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e) {
				e.printStackTrace();
			} catch (TerraException e) {
				for(String string : Txt.parseWrap(e.getMessages())){
					sender.sendMessage(string);
				}
			}
			
			i++;
		}
		
		try { method.invoke(handler, arguments); }
		catch (Exception e){
			sender.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" + ChatColor.WHITE + "] An error occurred while trying to process the command");
			String commandText = command.getName();
			for(String arg : args){ commandText += " " + arg; }
			TerraLogger.debug("Full Stack Trace printed to Console");
			e.printStackTrace();
		}
		return true;
	}
	
	public Entry<CommandAbstract, List<String>> getSubCommandMap(CommandAbstract parent, List<String> args){
		if (parent.getSubCommands().isEmpty() | args.isEmpty()){
			Entry<CommandAbstract, List<String>> result = new AbstractMap.SimpleEntry<CommandAbstract, List<String>>(parent, args); 
			return result;
		}
		
		if (getSubCommand(args.get(0), parent) == null){
			Entry<CommandAbstract, List<String>> result = new AbstractMap.SimpleEntry<CommandAbstract, List<String>>(parent, args); 
			return result;
		}
		
		CommandAbstract child = getSubCommand(args.get(0), parent);
		args.remove(0);
		return getSubCommandMap(child, args);
	}
}
