package uk.co.terragaming.code.terracraft.StaffMechanics.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics.ItemMechanics;

public class StaffSpawnCustomItem implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (args.length >= 1){
			
			ItemManager itemManager = ItemMechanics.getInstance().getItemManager();
			
			if (itemManager.hasItem(args[0])){
				player.getInventory().addItem(itemManager.getItemStack(args[0]));
			} else {
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Unregistered ItemName.");
			}
		} else {
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Invalid Command Usage.");
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Usage: /staff spawnCustomItem <ItemName>");
		}
		return true;
	}
	
}
