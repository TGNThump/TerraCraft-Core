package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.commands;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.TagArg;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemFactory;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.Item;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.oldItemMechanics;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemRegistry;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class StaffItemCommands {
	
	@Command({ "item", "i" })
	@CommandDescription("Staff Item Command Set")
	@CommandParent("staff")
	@HelpCommand
	public void onStaffItemCommand(Player sender, Language language) {
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff item help"));
	}
	
	@Command({ "rand", "r" })
	@CommandDescription("Create random Item")
	@CommandParent("staff item")
	public void onStaffRandomItemCreate(Player sender){
		Item item = ItemFactory.randomItem();
		sender.getInventory().addItem(item.render());
	}
	
	@Command({ "create", "c" })
	@CommandDescription("Create an Item")
	@CommandParent("staff item")
	public void onStaffItemCreateCommand(Player sender, @TagArg boolean perm, Integer itemId) {
		ItemRegistry registry = oldItemMechanics.getInstance().getItemRegistry();
		
		if (registry.hasItem(itemId)) {
			ItemInstance item = registry.getItem(itemId).createInstance();
			Account account = AccountRegistry.getAccount(sender);
			Character character = account.getActiveCharacter();
			item.setCharacter(character);
			if (character == null) {
				if (perm) {
					oldItemMechanics.getInstance().getItemInstanceRegistry().addItemIfAbsent(item);
					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Permanent Item: " + item.getColouredName()));
				} else {
					try {
						oldItemMechanics.getInstance().getItemInstanceDao().delete(item);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Temporary Item: " + item.getColouredName()));
				}
			} else {
				if (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT)) {
					oldItemMechanics.getInstance().getItemInstanceRegistry().addItemToCharacter(character, item);
					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Item %s.", item.getColouredName()));
				} else {
					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You cannot spawn items without being in staff mode."));
					return;
				}
			}
			
			sender.getInventory().addItem(item.getItemStack());
		} else {
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Unregistered ItemId: " + itemId);
		}
	}
	
//	@Command({ "clearcache", "clearCache" })
//	@CommandDescription("Clear the Item Cache")
//	@CommandParent("staff item")
//	public void onClearItemCache(Player sender) {
//		ItemMechanics.getInstance().getItemDao().clearObjectCache();
//		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Cleared Item Cache"));
//	}
}
