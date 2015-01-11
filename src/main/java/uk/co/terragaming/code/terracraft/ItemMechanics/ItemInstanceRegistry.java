package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.HashMap;

public class ItemInstanceRegistry {
	private HashMap<Integer, ItemInstance> itemInstances = new HashMap<Integer, ItemInstance>();
	private HashMap<Integer, Integer> charItemInstances = new HashMap<Integer, Integer>();
	
	public boolean hasItem(Integer id){
		return itemInstances.containsKey(id);
	}
	
	public ItemInstance getItemInstance(Integer id){
		return itemInstances.get(id);
	}
	
	public void addItemInstance(Integer id, ItemInstance itemInstance){
		itemInstances.put(id, itemInstance);
	}
	
	public void addItemInstance(Integer id, Integer charId, ItemInstance itemInstance){
		itemInstances.put(id, itemInstance);
		charItemInstances.put(charId, id);
	}
}
