package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemRegistry;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class StaffItemCommands {

	@Command({"item", "i"})
	@CommandDescription("Staff Item Command Set")
	@CommandParent("staff")
	@HelpCommand
	public void onStaffItemCommand(Player sender, Language language){
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff item help"));
	}
	
	@Command({"create", "c"})
	@CommandDescription("Create an Item")
	@CommandParent("staff item")
	public void onStaffItemCreateCommand(Player sender, Integer itemId){
		ItemRegistry registry = ItemMechanics.getInstance().getItemRegistry();
		
		if (registry.hasItem(itemId)){
			ItemInstance item = registry.getItem(itemId).createInstance();
			Account account = AccountMechanics.getInstance().getRegistry().getAccount(sender);
			Character character = account.getActiveCharacter();
			item.setCharacter(character);
			ItemMechanics.getInstance().getItemInstanceRegistry().addItemToCharacter(character, item);
			sender.getInventory().addItem(item.getItemStack());
		} else {
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Unregistered ItemId: " + itemId);
		}
	}
}
