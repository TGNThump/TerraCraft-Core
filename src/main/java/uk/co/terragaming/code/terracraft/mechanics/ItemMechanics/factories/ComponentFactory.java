package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import java.lang.reflect.Field;
import java.util.ArrayList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.annotations.GlobalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.annotations.LocalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemComponentRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemBaseComponentData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemBaseComponentLink;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemComponentDataEntry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentLink;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;


public class ComponentFactory {
	
	public static ItemComponent create(ItemBaseComponentLink baseLink, ItemInstanceComponentLink instanceLink){
		Class<? extends ItemComponent> c = ItemComponentRegistry.getComponentClass(baseLink.getComponentId());
		ItemComponent component;
		
		try {
			component = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
		ArrayList<ItemComponentDataEntry> data = Lists.newArrayList();
		data.addAll(baseLink.getData());
		data.addAll(instanceLink.getData());		
		
		for (ItemComponentDataEntry entry : data){
			applyData(component, entry);
		}
		
		component.setBaseLinkId(baseLink.getId());
		component.setInstanceLinkId(instanceLink.getId());
		component.setInstanceLink(instanceLink);
		
		return component;
	}
	
	public static ItemComponent applyData(ItemComponent component, ItemComponentDataEntry data){
		Class<? extends ItemComponent> c = component.getClass();
		
		try {
			Field f = c.getDeclaredField(data.getFieldName());
			
			if (fieldIsGlobal(f) && data instanceof ItemBaseComponentData){
				f.setAccessible(true);
				Object value = data.getFieldValue();
				f.set(component, value);
				return component;
			}
			
			if (fieldIsLocal(f) && data instanceof ItemInstanceComponentData){
				f.setAccessible(true);
				Object value = data.getFieldValue();
				f.set(component, value);
				return component;
			}
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return component;
		}
		
		TerraLogger.error("Could not set " + data.getFieldName() + " from database, scope was wrong.");
		return component;
	}
	
	private static boolean fieldIsGlobal(Field f){
		return (f.getAnnotation(GlobalProperty.class) != null);
	}
	
	private static boolean fieldIsLocal(Field f){
		return (f.getAnnotation(LocalProperty.class) != null);
	}
}
