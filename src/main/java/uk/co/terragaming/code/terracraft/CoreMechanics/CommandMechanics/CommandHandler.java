package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.CommandArgument;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;

public class CommandHandler implements CommandExecutor{

	private final static HashMap<String, CommandAbstract> commands = new HashMap<String, CommandAbstract>(); // Including parents recursivly, Used for checking parents in registration
	private final static HashMap<String, CommandAbstract> rootCommands = new HashMap<String, CommandAbstract>(); // Includes aliases
	
	public static void registerCommands(JavaPlugin plugin, Object handler){
		
		// Sort methods by number of parent chunks
		
		List<Method> methods = Lists.newArrayList(handler.getClass().getMethods());
		Collections.sort(methods, new Comparator<Method>() {
			  @Override public int compare(final Method m1, final Method m2) {
				  if (!(m1.isAnnotationPresent(CommandParent.class) && m2.isAnnotationPresent(CommandParent.class))){ return 0;}
				  if (!m1.isAnnotationPresent(CommandParent.class)){return -1;}
				  if (!m2.isAnnotationPresent(CommandParent.class)){return 1;}
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
		
		// For each method in handler class...
		for (Iterator<Method> iterator = methods.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			
			// ... if the method parameters are correct ...
			Class<?>[] params = method.getParameterTypes();
			if (params.length != 2){ continue; }
			if (!CommandSender.class.isAssignableFrom(params[0])){ continue; }
			if (!CommandArgument[].class.isAssignableFrom(params[1])){ continue; }
			
			// ... and the method has a @Command annotation ...
			if (!method.isAnnotationPresent(Command.class)){ continue; }
			Command commandAnnotation = method.getAnnotation(Command.class);
			
			// ... grab the commandName and commandAliases from the @Command annotation ...
			String commandName = commandAnnotation.value()[0];
			List<String> commandAliases = Lists.newArrayList(commandAnnotation.value()); 
			commandAliases.remove(0);
			
			// ... if the method has a @CommandParent annotation ...
			if (method.isAnnotationPresent(CommandParent.class)){
				// Grab the parent command ...
				CommandParent commandParent = method.getAnnotation(CommandParent.class);
				String parentName = commandParent.value();
				if (!commands.containsKey(parentName)){ TerraLogger.debug("A command cannot be registered before its parent: " + handler.getClass().getName() + " [" + method.getName() + "]"); continue; }
				CommandAbstract parent = commands.get(parentName);
				
				// ... and create the child command ...
				CommandSub child = new CommandSub(commandName);
				child.setParent(parent);
				child.setHandler(handler);
				child.setMethod(method);
				
				if (commandAliases.size() > 0){
					child.setAliases(commandAliases);
				}
				
				// ... and if the method has a @CommandDescription annotation ...
				if (!method.isAnnotationPresent(CommandDescription.class)){
					CommandDescription commandDescription = method.getAnnotation(CommandDescription.class);
					
					// ... set the commandDescription ...
					child.setDescription(commandDescription.value());
				}
				
				// ... and if the method has a @CommandUsage annotation ...
				if (!method.isAnnotationPresent(CommandUsage.class)){
					CommandUsage commandUsage = method.getAnnotation(CommandUsage.class);
					
					// ... set the commandUsage ...
					child.setUsage(commandUsage.value());
				}
				
				// ... and add the child command as a child in the parent command.
				parent.addChild(child);
				commands.put(child.getPath(), child);
				TerraLogger.debug("Registered Command: /" + child.getPath());
			}
			// .. or if it is a root command ...
			else {
				// Get the bukkit command ...
				if (plugin.getCommand(commandName) == null){ TerraLogger.debug("Failed to register command: " + commandAnnotation.toString()); continue; }
				PluginCommand pc = plugin.getCommand(commandName);
				
				// ... set the command aliases ...
				if (commandAliases.size() > 0){
					pc.setAliases(commandAliases);
				}
				
				// ... set the command executor ...
				pc.setExecutor(new CommandHandler());
				
				// ... and create a CommandRoot ...
				CommandRoot command = new CommandRoot(pc);
				command.setHandler(handler);
				command.setMethod(method);
				
				// ... and if the method has a @CommandDescription annotation ...
				if (!method.isAnnotationPresent(CommandDescription.class)){
					CommandDescription commandDescription = method.getAnnotation(CommandDescription.class);
					
					// ... set the commandDescription ...
					command.setDescription(commandDescription.value());
				}
				
				// ... and if the method has a @CommandUsage annotation ...
				if (!method.isAnnotationPresent(CommandUsage.class)){
					CommandUsage commandUsage = method.getAnnotation(CommandUsage.class);
					
					// ... set the commandUsage ...
					command.setUsage(commandUsage.value());
				}
				
				commands.put(command.getPath(), command);
				rootCommands.put(command.getPath(), command);
				if (!command.getAliases().isEmpty()){
					for(String alias : command.getAliases()){
						rootCommands.put(alias, command);
					}
				}
				
				TerraLogger.debug("Registered Command: /" + command.getPath());
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] arguments) {
		CommandAbstract command = rootCommands.get(bukkitCommand.getName());;
		List<String> args;
		
		// If the command has at least 1 arg [possible subcommand] ...
		if (arguments.length > 0){
			Object[] result = getChildByArgList(command, Lists.newArrayList(arguments));
			
			command = (CommandAbstract) result[0];
			args = (List<String>) result[1];
		} else {
			args = Lists.newArrayList(arguments);
		}
		
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
		
		//TODO: CommandArgs and CommandReqs
		
		CommandArgument[] cArgs = new CommandArgument[0];
		
		try { method.invoke(handler, sender, cArgs); }
		catch (Exception e){
			sender.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" + ChatColor.WHITE + "] An error occurred while trying to process the command");
			String commandText = command.getName();
			for(String arg : args){ commandText += " " + arg; }
			TerraLogger.debug("Error occurred processing command '" + commandText  + "' for " + sender.getName());
			TerraLogger.debug(e.getLocalizedMessage());
			TerraLogger.debug("Full Stack Trace printed to Console");
			e.printStackTrace();
		}
		return true;
	}
	
	public Object[] getChildByArgList(CommandAbstract parent, List<String> args){
		if (parent.getChildren().isEmpty() | args.isEmpty()){
			Object[] result = {parent, args}; 
			return result;
		}
		
		if (!parent.getChildren().containsKey(args.get(0))){
			args.remove(0);
			Object[] result = {parent, args}; 
			return result;
		}
		
		CommandAbstract child = parent.getChildren().get(args.get(0));
		args.remove(0);
		return getChildByArgList(child, args);
	}

}
