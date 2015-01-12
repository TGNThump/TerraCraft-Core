package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.HashMap;
import java.util.HashSet;

public class ItemInstanceRegistry {
	private HashMap<Integer, ItemInstance> instances1 = new HashMap<Integer, ItemInstance>();
	private HashMap<Integer, HashSet<Integer>> instances2 = new HashMap<Integer, HashSet<Integer>>(); // [CharId, InstanceId[]]
	
	public boolean hasItemInstance(Integer id){
		return instances1.containsKey(id);
	}
	
	public ItemInstance getItemInstance(Integer id){
		return instances1.get(id);
	}
	
	public ItemInstance[] getItemInstances(Integer charId){
		HashSet<Integer> instanceIds = instances2.get(charId);
		ItemInstance[] instances = new ItemInstance[instanceIds.size()];
		int i = 0;
		
		for(Integer instanceId : instanceIds){
			instances[i] = instances1.get(instanceId);
			i++;
		}
		
		return instances;
	}

	public void addItemInstance(Integer id, ItemInstance instance){
		instances1.put(id, instance);
	}
	
	public void addItemInstance(Integer id, Integer charId, ItemInstance instance){
		instances1.put(id, instance);
		instances2.get(charId).add(id);
	}
	
	public void addItemInstance(ItemInstance instance){
		instances1.put(instance.getId(), instance);
	}
	
	public void addItemInstance(ItemInstance instance, Integer charId){
		instances1.put(instance.getId(), instance);
		if (instances2.containsKey(instance.getOwnerId())){
			instances2.get(instance.getOwnerId()).add(instance.getId());
		} else {
			instances2.put(instance.getOwnerId(), new HashSet<Integer>());
			instances2.get(instance.getOwnerId()).add(instance.getId());
		}
		
	}
	
}