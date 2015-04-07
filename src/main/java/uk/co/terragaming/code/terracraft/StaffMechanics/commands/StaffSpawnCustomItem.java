package uk.co.terragaming.code.terracraft.StaffMechanics.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemRegistry;

public class StaffSpawnCustomItem implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (args.length >= 1){
			
			ItemRegistry itemRegistry = ItemMechanics.getInstance().getItemRegistry();
			Integer itemId;
			try{
				itemId = Integer.decode(args[0]);
			} catch (Exception e){
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Invalid Command Usage.");
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Usage: /staff spawnCustomItem <ItemId>");
				return true;
			}
			if (itemRegistry.hasItem(itemId)){
				ItemInstance item = itemRegistry.getItem(itemId).createInstance();
				Account account = AccountMechanics.getInstance().getRegistry().getAccount(player);
				Character character = CharacterMechanics.getInstance().getAccountActiveCharater(account);
				
				item.setOwnerId(account.getId());
				ItemMechanics.getInstance().getItemInstanceRegistry().addItemInstance(item, character.getId());
				
				player.getInventory().addItem(item.getItemStack());
			} else {
				sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Unregistered ItemId: " + itemId + " from " + args[0]);
			}
		} else {
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Invalid Command Usage.");
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Usage: /staff spawnCustomItem <ItemId>");
		}
		return true;
	}
	
}
