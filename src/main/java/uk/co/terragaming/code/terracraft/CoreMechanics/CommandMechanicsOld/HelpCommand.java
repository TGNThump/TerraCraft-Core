package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanicsOld;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {
	public String mechanicName;
	private List<CustomCommand> commandsList = new ArrayList<CustomCommand>();
	
	public HelpCommand(String mechanicName){
        this.mechanicName = mechanicName;
	}
	
	public void registerCommand(CustomCommand command){
		if (command == null) {
            throw new IllegalArgumentException("Invalid command paramters specified.");
        }
		commandsList.add(command);
	}
	
	public void registerCommand(String command, String desc, String usage){
		if (command.isEmpty() || desc.isEmpty() || usage.isEmpty()) {
            throw new IllegalArgumentException("Invalid command paramters specified.");
        }
		commandsList.add(new CustomCommand(command, desc, usage));
	}
	
	public void unregisterCommand(CustomCommand command) {
		commandsList.remove(command);
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Integer commandCount = commandsList.size();
		CustomCommand[] commands = new CustomCommand[commandCount];
		commands = commandsList.toArray(commands);
		
		Integer pageCurrent;
		Integer pageMax = (commandCount % 10 == 0) ? commandCount / 5 : (commandCount / 5) + 1;
		
		if (args.length > 0){
			try{
				pageCurrent = Integer.parseInt(args[0]);
			} catch (Exception e){
				pageCurrent = 1;
			}
			
			if (pageCurrent.equals(0) || pageCurrent > pageMax){
				pageCurrent = 1;
			}
		} else {
			pageCurrent = 1;
		}
		
		sender.sendMessage("");
		sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] ----- " + ChatColor.AQUA + mechanicName + " Help "  + ChatColor.WHITE + "[Page " + pageCurrent.toString() + " / " + pageMax.toString() + "] -----");
		
		for(int i=(pageCurrent*5)-5; i < pageCurrent*5; i++){
			if (i >= commands.length){ break; }
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Usage: " + ChatColor.GREEN + commands[i].usage);
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Description: " + ChatColor.WHITE + commands[i].desc);
			sender.sendMessage("");
		}

		return true;
	}
}