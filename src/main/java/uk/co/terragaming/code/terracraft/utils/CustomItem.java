package uk.co.terragaming.code.terracraft.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem implements Listener {
	public ItemStack item;
	private ItemMeta itemMeta;
	public ArrayList<String> lore = new ArrayList<String>();

	public CustomItem(Material material) {
		this.item = new ItemStack(material);
		this.itemMeta = item.getItemMeta();
	}

	public ItemStack getItemStack() {
		item.setItemMeta(itemMeta);
		return item;
	}

	public ItemMeta getItemMeta() {
		return itemMeta;
	}

	public void setName(String displayName) {
		itemMeta.setDisplayName(displayName);
	}

	public void setLore(Integer loreId, String lore) {
		this.lore.set(loreId, lore);
		itemMeta.setLore(this.lore);
	}

	public void addLore(String lore) {
		this.lore.add(lore);
		itemMeta.setLore(this.lore);
	}

	public void removeLore(Integer loreId) {
		this.lore.remove(loreId);
		itemMeta.setLore(this.lore);
	}

	public void setDurability(Short durability) {
		item.setDurability(durability);
	}

	public void addEnchantment(Enchantment ent, Integer level) {
		itemMeta.addEnchant(ent, level, true);
	}
}