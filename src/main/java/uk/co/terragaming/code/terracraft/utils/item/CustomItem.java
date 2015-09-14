package uk.co.terragaming.code.terracraft.utils.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class CustomItem implements Listener {
	
	public ItemStack item;
	private ItemMeta itemMeta;
	public ArrayList<String> lore = new ArrayList<String>();
	
	public CustomItem(Material material) {
		item = new ItemStack(material);
		itemMeta = item.getItemMeta();
	}
	
	public ItemStack getItemStack() {
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public ItemMeta getItemMeta() {
		return itemMeta;
	}
	
	public void setName(String displayName) {
		displayName = Txt.parse(displayName);
		itemMeta.setDisplayName(displayName);
	}
	
	public void setLore(Integer loreId, String lore) {
		lore = Txt.parse(lore);
		this.lore.set(loreId, lore);
		itemMeta.setLore(this.lore);
	}
	
	public void addLore(String lore) {
		lore = Txt.parse(lore);
		this.lore.add(lore);
		itemMeta.setLore(this.lore);
	}
	
	public void addLore(List<String> loreList) {
		for (String lore : loreList){
			addLore(lore);
		}
	}
	
	public void removeLore(Integer loreId) {
		lore.remove(loreId);
		itemMeta.setLore(lore);
	}
	
	public void setDurability(Short durability) {
		item.setDurability(durability);
	}
	
	public void setStackSize(Integer size){
		item.setAmount(size);
	}
	
	public void addEnchantment(Enchantment ent, Integer level) {
		itemMeta.addEnchant(ent, level, true);
	}
}