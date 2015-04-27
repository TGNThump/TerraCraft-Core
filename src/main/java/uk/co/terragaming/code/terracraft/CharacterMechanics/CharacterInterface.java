package uk.co.terragaming.code.terracraft.CharacterMechanics;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics.CustomItem;
import uk.co.terragaming.code.terracraft.utils.IconMenu;
import uk.co.terragaming.code.terracraft.utils.IconMenu.Row;
import uk.co.terragaming.code.terracraft.utils.IconMenu.onClick;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.comphenix.attribute.AttributeStorage;

public class CharacterInterface {

	public CharacterInterface(UUID playerUUID){
		showCharacterSelectorInterface(playerUUID);
	}
	
	public void showCharacterSelectorInterface(final UUID playerUUID){
		Account account = AccountMechanics.getInstance().getRegistry().getAccount(playerUUID);
		Player player = TerraCraft.Server().getPlayer(playerUUID);
		player.setHealth(20d);
		player.setFoodLevel(200);
		player.getInventory().clear();
		player.setGameMode(GameMode.CREATIVE);
		
		IconMenu menu = new IconMenu(ChatColor.WHITE + "Your Character Profiles", 1, new onClick(){
			
			@Override
			public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
				if (row.getRow() == 0){
					if (item != null) {
						AttributeStorage storage = AttributeStorage.newTarget(item, TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft"));
						
						if (storage.getData(null) != null){
							Integer charId = Integer.parseInt(storage.getData(null).substring(5));
							Account account = AccountMechanics.getInstance().getRegistry().getAccount(clicker);
							Character activeChar = CharacterMechanics.getInstance().getCharacter(charId);
							account.setCurCharacterId(charId);
							activeChar.setActiveCharacter();
						} else {
							menu.close(clicker);
							showNewCharacterInterface(clicker);
						}
					}
				}				
				return true;
			}
			
		});
		int i = 0;
		Character[] chars = CharacterMechanics.getInstance().getCharactersByAccount(account);
		for (Character character1 : chars){
			if(i == 8){
				TerraLogger.error(account.getTerraTag() + "has more than 8 characters");
				break;
			}
			CustomItem item = new CustomItem(Material.SKULL_ITEM);
			
			item.setName(ChatColor.GOLD + character1.getName());
			item.addLore(ChatColor.DARK_AQUA + "Click here to become " + character1.getName() + ".");
			AttributeStorage storage = AttributeStorage.newTarget(item.getItemStack(), TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft"));
			storage.setData("CID: " + character1.getId());
			
			menu.addButton(menu.getRow(0), i, storage.getTarget());
			i++;
		}
		
		menu.addButton(menu.getRow(0), 8, new ItemStack(Material.BOOK_AND_QUILL), ChatColor.GOLD + "New Character", ChatColor.DARK_AQUA + "Click here to create a new RP Character");
		menu.open(player);
	}
	
	public void showNewCharacterInterface(Player player){
			IconMenu menu = new IconMenu("New Character: Select Race", 1, new onClick(){
			
			@Override
			public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
				if (row.getRow() == 0){
					TerraLogger.debug("Player " + clicker.getDisplayName() + " clicked " + ChatColor.stripColor(row.getRowItem(slot).getItemMeta().getDisplayName()).toLowerCase());
					switch(ChatColor.stripColor(row.getRowItem(slot).getItemMeta().getDisplayName()).toLowerCase()){
					case "human":
						
					case "orc":
						
					case "elf":
						
					case "dwarf":
						
					}
				
				}				
				return true;
			}
			
		});
		
		menu.addButton(menu.getRow(0), 0, new ItemStack(Material.SKULL_ITEM), ChatColor.GOLD + "Human", ChatColor.DARK_AQUA + "Click here to become a Human");
		menu.addButton(menu.getRow(0), 1, new ItemStack(Material.SKULL_ITEM), ChatColor.GOLD + "Orc", ChatColor.DARK_AQUA + "Click here to become a Orc");
		menu.addButton(menu.getRow(0), 2, new ItemStack(Material.SKULL_ITEM), ChatColor.GOLD + "Elf", ChatColor.DARK_AQUA + "Click here to become a Elf");
		menu.addButton(menu.getRow(0), 3, new ItemStack(Material.SKULL_ITEM), ChatColor.GOLD + "Dwarf", ChatColor.DARK_AQUA + "Click here to become a Dwarf");
		menu.open(player);
	}
}
