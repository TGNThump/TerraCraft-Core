package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Array;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Java15Compat;

public class CommandManager implements CommandExecutor {
	public String command;
	public String alias;
	public JavaPlugin plugin;
	public HelpCommand help;
	private HashMap<String, CommandExecutor> commandMap = new HashMap<String, CommandExecutor>();
	
	public CommandManager(JavaPlugin plugin, String command){
		this(plugin, command, "");
	}
	
	public CommandManager(JavaPlugin plugin, String command, String alias){
		if (plugin == null) throw new IllegalArgumentException("Plugin cannot be null.");
        this.plugin = plugin;
        this.command = command;
        this.alias = alias;
        
        String mechanicName = this.command.substring(0, 1).toUpperCase() + this.command.substring(1).toLowerCase() + " Mechanics";
        help = new HelpCommand(mechanicName);
        registerCommand("help", help, "Gets this help message", "/" + command + " help [Page Number]");
	}
	
	public void registerCommand(String command, CommandExecutor commandExecutor, String desc, String usage){
		if (commandExecutor == null || command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Invalid command paramters specified.");
        }
        this.commandMap.put(command.toLowerCase(), commandExecutor);
        help.registerCommand(command, desc, usage);
	}
	
	public void registerCommand(String command, CommandExecutor commandExecutor){
		if (commandExecutor == null || command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Invalid command paramters specified.");
        }
        this.commandMap.put(command.toLowerCase(), commandExecutor);
	}
	
	public void unregisterCommand(String command) {
        this.commandMap.remove(command.toLowerCase());
    }
	
	public CommandExecutor getCommandExecutor(String command) {
        return this.commandMap.get(command.toLowerCase());
    }

	public boolean hasCommand(String command) {
        return this.commandMap.containsKey(command.toLowerCase());
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (command.equalsIgnoreCase(label) || alias.equalsIgnoreCase(label)){
			String mechanicName = this.command.substring(0, 1).toUpperCase() + this.command.substring(1).toLowerCase() + " Mechanics";
			String permissionName = this.command.substring(0, 1).toUpperCase() + this.command.substring(1).toLowerCase() + "Mechanics";
			if (sender.hasPermission("TerraCraft." + permissionName) || sender.isOp()){
		        if (args.length >= 1 && hasCommand(args[0])) {
		            CommandExecutor executor = this.getCommandExecutor(args[0]);
		            return executor.onCommand(sender, cmd, label, popArray(args));
		        } else {
		        	sender.sendMessage("");
		        	sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] ----- [" + ChatColor.AQUA + mechanicName + ChatColor.WHITE + "] -----");
					sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Use " + ChatColor.GREEN + "/" + this.command + " help" + ChatColor.WHITE + " for a list of commands");
					sender.sendMessage("");
					return true;
		        }
			} else {
				sender.sendMessage("");
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] You do not have permission to use this command.");
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "]");
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] If you belive this to be an error,");
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] please contact the TerraCraft Development Team.");
				sender.sendMessage("");
				return true;
			}
			
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static final <T> T[] popArray(T[] args) {
        return (args.length >= 2) ? Java15Compat.Arrays_copyOfRange(args, 1, args.length) :
            (T[]) Array.newInstance(args.getClass().getComponentType(), 0);
    }
}
