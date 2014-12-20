package uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics;

import java.util.HashMap;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemManager{
	public PluginManager pm;
	public JavaPlugin plugin;
	
	private HashMap<String, ItemStack> itemMap = new HashMap<String, ItemStack>();

	public ItemManager(PluginManager pm, JavaPlugin plugin){
		if (plugin == null || pm == null) throw new IllegalArgumentException("Args cannot be null.");
		this.pm = pm;
		this.plugin = plugin;
	}
	
	public void registerItem(String itemName, ItemStack item, Listener listener){
		if (itemName == "" || item == null || listener == null) {
            throw new IllegalArgumentException("Invalid command paramters specified.");
        }
        this.itemMap.put(itemName.toLowerCase(), item);
        pm.registerEvents(listener, plugin);
	}
	
	public void registerItem(String itemName, CustomItem customItem) {
		if (itemName == "" || customItem == null) {
            throw new IllegalArgumentException("Invalid command paramters specified.");
        }
        this.itemMap.put(itemName.toLowerCase(), customItem.item);
        pm.registerEvents(customItem, plugin);
	}
	
	
	public void unregisterItem(String itemName) {
        this.itemMap.remove(itemName.toLowerCase());
    }
	
	public ItemStack getItemStack(String itemName) {
        return this.itemMap.get(itemName.toLowerCase());
    }
	
	public boolean hasItem(String itemName) {
        return this.itemMap.containsKey(itemName.toLowerCase());
    }	
}