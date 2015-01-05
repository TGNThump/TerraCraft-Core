package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.HashMap;

public class ItemInstanceRegistry {
	private HashMap<Integer, ItemInstance> itemInstances = new HashMap<Integer, ItemInstance>();
	
	public boolean hasItem(Integer id){
		return itemInstances.containsKey(id);
	}
	
	public ItemInstance getItemInstance(Integer id){
		return itemInstances.get(id);
	}
	
	public void addItemInstance(Integer id, ItemInstance itemInstance){
		itemInstances.put(id, itemInstance);
	}
}
