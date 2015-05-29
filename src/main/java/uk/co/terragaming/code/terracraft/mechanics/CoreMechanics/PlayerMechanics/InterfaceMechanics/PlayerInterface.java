package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.CallBack;

public class PlayerInterface {
	
	public String title;
	public int size;
	public boolean closable = true;
	
	public HashMap<Integer, Entry<ItemStack, CallBack>> items;
	public HashMap<UUID, PlayerInterfaceInstance> instances;
	
	public PlayerInterface(String title, int rows) {
		this.title = title;
		size = rows * 9;
		items = new HashMap<>();
		instances = new HashMap<>();
	}
	
	public boolean addIcon(int row, int position, ItemStack item, CallBack callback) {
		if (items.containsKey(row * 9 + position))
			return false;
		items.put(row * 9 + position, new AbstractMap.SimpleEntry<ItemStack, CallBack>(item, callback));
		return true;
	}
	
	public boolean addIcon(ItemStack item, CallBack callback) {
		for (int i = 0; i < size; i++) {
			if (items.containsKey(i)) {
				continue;
			}
			items.put(i, new AbstractMap.SimpleEntry<ItemStack, CallBack>(item, callback));
			return true;
		}
		return false;
	}
	
	public PlayerInterfaceInstance createInstance(Player player) {
		PlayerInterfaceInstance instance = new PlayerInterfaceInstance(player, this);
		instance.create();
		instance.register();
		UUID uuid = player.getUniqueId();
		instances.put(uuid, instance);
		return instance;
	}
	
	// Static
	
	public static ItemStack item(ItemStack item, String name, String... lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
