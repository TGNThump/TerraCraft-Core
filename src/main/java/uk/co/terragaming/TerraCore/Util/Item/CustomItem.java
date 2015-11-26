package uk.co.terragaming.TerraCore.Util.Item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uk.co.terragaming.TerraCore.Util.Text.Text;

public class CustomItem {
	
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
		displayName = Text.of(displayName);
		itemMeta.setDisplayName(displayName);
	}
	
	public void setLore(Integer loreId, String lore) {
		lore = Text.of(lore);
		this.lore.set(loreId, lore);
		itemMeta.setLore(this.lore);
	}
	
	public void addLore(String lore) {
		lore = Text.of(lore);
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