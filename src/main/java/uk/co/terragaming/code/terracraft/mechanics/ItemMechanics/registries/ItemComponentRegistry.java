package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemComponentData;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.j256.ormlite.dao.Dao;


public class ItemComponentRegistry {
	
	private static ItemComponentRegistry instance;
	
	private HashMap<Integer, Class<? extends ItemComponent>> components;
	
	public void init(){
		instance = this;
		components = new HashMap<>();
	}
	
	public static Class<? extends ItemComponent> getComponentClass(ItemComponentData data){
		return getComponentClass(data.getId());
	}
	
	public static Class<? extends ItemComponent> getComponentClass(Integer id){
		return instance.components.get(id);
	}
	
	public static void registerComponents(Dao<ItemComponentData, Integer> dao){
		for (ItemComponentData data : dao){
			registerComponent(data);
		}
		TerraLogger.info("Registered ItemComponents to Registry");
	}
	
	@SuppressWarnings("unchecked")
	public static void registerComponent(ItemComponentData data){
		Integer id = data.getId();
		String name = instance.getClass().getName();
		String[] parts = name.split("\\.");
		parts = ArrayUtils.remove(parts, parts.length - 1);
		parts = ArrayUtils.remove(parts, parts.length - 1);
		String path = Txt.implode(parts, ".") + ".components.";
		String className = path + data.getClassName();
		
		Class<?> c;
		
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			TerraLogger.error("Failed to register ItemComponent " + data.getName() + "[" + id + "]");
			e.printStackTrace();
			return;
		}
		
		Class<? extends ItemComponent> cc;
		
		try{
			cc = (Class<? extends ItemComponent>) c;
		} catch (Exception e){
			e.printStackTrace();
			TerraLogger.error("ItemComponent " + data.getName() + "[" + id + "] is not assignable from ItemComponent");
			return;
		}
		
		instance.components.put(id, cc);
	}
}
