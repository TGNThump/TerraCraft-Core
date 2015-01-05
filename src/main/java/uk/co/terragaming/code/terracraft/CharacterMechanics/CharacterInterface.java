package uk.co.terragaming.code.terracraft.CharacterMechanics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.utils.IconMenu;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.IconMenu.Row;
import uk.co.terragaming.code.terracraft.utils.IconMenu.onClick;

public class CharacterInterface {

	public CharacterInterface(Player player){
		showCharacterSelectorInterface(player);
	}
	
	public void showCharacterSelectorInterface(final Player player){
		IconMenu menu = new IconMenu("Your Character Profiles", 1, new onClick(){
			
			@Override
			public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
				if (row.getRow() == 0){
					TerraLogger.debug("Player " + clicker.getDisplayName() + " clicked " + ChatColor.stripColor(row.getRowItem(slot).getItemMeta().getDisplayName()).toLowerCase());
					
					switch(ChatColor.stripColor(row.getRowItem(slot).getItemMeta().getDisplayName()).toLowerCase()){
					case "new character":
						menu.close(player);
						showNewCharacterInterface(player);
					}
				}				
				return true;
			}
			
		});
		
		menu.addButton(menu.getRow(0), 0, new ItemStack(Material.SKULL_ITEM), ChatColor.GOLD + "Character 1", ChatColor.DARK_AQUA + "Click here to become Character 1");
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
