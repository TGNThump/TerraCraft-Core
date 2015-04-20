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
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.TagArg;
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
				// If both methods are commands ...
				if ((!isCommand(m1)) | (!isCommand(m2))){ return 0; }
				
				if (!hasParent(m1) && !hasParent(m2)){ return 0; }
				if (hasParent(m1) && !hasParent(m2)){ return 1; }
				if (hasParent(m2) && !hasParent(m1)){ return -1; }

				Integer m1l = getParent(m1).split(" ").length;
				Integer m2l = getParent(m1).split(" ").length;
				
				TerraLogger.debug(m1.getName() + " [" + m1l + "], " + m2.getName() + " [" + m2l + "]");
				
				if (m1l < m2l){
					return -1;
				} else if (m2l > m1l){
					return 1;
				} else {
					return 0;
				}
			}
		});
		
		return methods;
	}
	
	private static boolean isCommand(Method method){
		return method.isAnnotationPresent(Command.class);
	}
	
	private static boolean hasParent(Method method){
		return method.isAnnotationPresent(CommandParent.class);
	}
	
	private static String getParent(Method method){
		return method.getAnnotation(CommandParent.class).value();
	}
	
	private static Optional<CommandAbstract> registerMethod(JavaPlugin plugin, Object handler, Method method){
		if (!method.isAnnotationPresent(Command.class)){ return Optional.empty(); }
		Command commandAnnotation = method.getAnnotation(Command.class);
		
		String name = "";
		List<String> aliases = Lists.newArrayList();
		String description = "";
		String usage = null;
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
		if (commandUsage == null) command.setUsage(generateUsage(command));
		else command.setUsage(commandUsage);
		
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
			createCommand(plugin, new CommandHelp(), CommandHelp.class.getMethod("onHelpCommand", CommandSender.class, CommandAbstract.class, Integer.class), "help", Lists.newArrayList(), "Shows this help message", "<c>/" + parent.getPath() + " help [pageNumber]", Optional.of(parent));
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
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] fullArgs){
		CommandAbstract command = rootCommands.get(bukkit.command.getName());
		List<String> commandArgs;
		
		// If the command has at least one argument ...
		if (fullArgs.length > 0){
			// ... Grab the subcommand by argument recursively, parsing the remaining arguments ...
			Entry<CommandAbstract, List<String>> result = getSubCommandFromArgs(command, fullArgs);
			
			command = result.getKey();
			commandArgs = result.getValue();
		}
		
		// ... then Get the command handler and method ...
		Object handler = command.getHandler();
		Method method = command.getMethod();
		
		if (handler == null | method == null){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Unknown command. Type \"<c>/" + command.getPath() + " help<r>\" for help."));
			return true;
		}
		
		// ... and the arguments from argumentReaders ...
		Object[] arguments = getArguments(commandArgs, method.getParameters(), sender, command);
		
		// ... and invoke the method with the arguments.
		try { method.invoke(handler, arguments); }
		catch (Exception e){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] An error occurred while trying to process the command"));
			e.printStackTrace();
		}
		return true;
	}
	
	public static String generateUsage(CommandAbstract command){
		Object handler = command.getHandler();
		Method method = command.getMethod();
		
		String usage = "<c>/" + command.getPath() + "<p>";
		
		int i = -1;
		
		for (Parameter param : method.getParameters()){
			i++;
			String paramName = param.getName();
			Class<?> type = param.getType();
			
			boolean isOptional = param.isAnnotationPresent(OptArg.class);
			boolean isTag = param.isAnnotationPresent(TagArg.class);
			
			if (i < 2 && (type.isAssignableFrom(CommandSender.class) || type.isAssignableFrom(CommandAbstract.class))){
				continue;
			}
			
			if (isTag){
				usage += " [-" + paramName + "]";
				continue;
			}
			
			if (isOptional){
				usage += " [" + paramName + "]";
				continue;
			} else {
				usage += " <" + paramName + ">";
			}
		}
	}
	
	public static Object[] getArguments(List<String> commandArgs, Paramater[] params, CommandSender sender, CommandAbstract command){
		Object[] args = new Object[params.length];
		
		int paramIndex = 0;
		int argIndex = 0;
		
		// ... and for each parameter ...
		for (Parameter param : params){
			// Grab the parameter name and type ...
			String paramName = param.getName();
			Class<?> type = param.getType();
			
			// ... and any @annotations and their values ...
			boolean isOptional = param.isAnnotationPresent(OptArg.class);
			boolean isTag = param.isAnnotationPresent(TagArg.class);
		
			String defaultValue = null;
			
			if (isOptional){ defaultValue = param.getAnnotation(OptArg.class).value(); }
			
			// ... and handle a tag argument ...
			
			if (isTag){
				if (argIndex > commandArgs.size()){
					if (commandArgs.get(argIndex).equals("-" + paramName)){
						args[paramIndex] = true;
						paramIndex++;
						argIndex++;
						continue;
					}
				}
			}
			
			// ... and if the parameter is special, assign its value ...
			if (argIndex < 2 && type.isAssignableFrom(CommandSender.class)){
				args[paramIndex] = sender;
				paramIndex++;
				continue;
			}
			if (argIndex < 2 && type.isAssignableFrom(CommandAbstract.class)){
				args[paramIndex] = command;
				paramIndex++;
				continue;
			}
			
			// ... Otherwise try ...
			try{
				// ... to get the argReader for the parameter type ...
				Class<?> arClass = Class.forName("uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.AR" + Txt.upperCaseFirst(type.getSimpleName()));
				AR<?> argReader = (AR<?>) arClass.newInstance();
				
				Object arg = null;
				
				// ... and if argIndex > the number of arguments ...
				if (argIndex > commandArgs.size()){
					
					// ... attempt to read the argument.
					arg = argReader.read(commandArgs.get(paramIndex), sender);
					argIndex++;
				
				// ... or if the parameter is optional ...
				} else if (isOptional) {
					
					// ... attempt to read the defaultValue.
					arg = argReader.read(defaultValue, sender);
				
				// ... otherwise ...
				} else {
					
					// ... throw a new TerraException - Incorrect Command Usage...
					TerraException ex = new TerraException();
					ex.addMessage(Txt.parse("<b>%s", "Incorrect Command Usage"));
					ex.addMessage(Txt.parse(command.getUsage()));
					throw ex;
				}
				
				args[paramIndex] = arg;
				
			} catch (TerraException e){
				for(String string : Txt.wrap(e.getMessages())){ 
 					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + string)); 
 				} 
 				return null; 
			}
			
			paramIndex++;
		}
		
		return args;
	}
	
	public Entry<CommandAbstract, List<String>> getSubCommandFromArgs(CommandAbstract parent, String[] args){
		return getSubCommandFromArgs(parent, Lists.newArrayList(args));
	}
	
	public Entry<CommandAbstract, List<String>> getSubCommandFromArgs(CommandAbstract parent, List<String> args){
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
		return getSubCommandFromArgs(child, args);
	}
}
