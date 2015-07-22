package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1;

import java.util.HashMap;

public class ItemRegistry {
	
	public static HashMap<Integer, Item> items = new HashMap<Integer, Item>();
	
	public boolean hasItem(Integer id) {
		return items.containsKey(id);
	}
	
	public Item getItem(Integer id) {
		return items.get(id);
	}
	
	public void addItem(Integer id, Item item) {
		items.put(id, item);
	}
}
