package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemBase;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.annotations.LocalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemBaseRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemComponentRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemBaseComponentLink;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentLink;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;


public class ItemFactory {
	
	public static ItemInstance create(Integer baseId){
		return create(ItemBaseRegistry.get(baseId));
	}
	
	@SuppressWarnings("unchecked")
	public static ItemInstance create(ItemBase itemBase){
		ItemInstance item = new ItemInstance();
		item.setBase(itemBase);
		
		Dao<ItemInstance, Integer> itemInstanceDao = (Dao<ItemInstance, Integer>) DatabaseMechanics.getInstance().getDao(ItemInstance.class);
		try {
			itemInstanceDao.create(item);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		for (ItemBaseComponentLink baseLink : itemBase.getComponentLinks()){
			ItemInstanceComponentLink instanceLink = new ItemInstanceComponentLink();
			instanceLink.setItemInstance(item);
			instanceLink.setComponentData(baseLink.getComponentData());
			
			Dao<ItemInstanceComponentLink, Integer> instanceLinkDao = (Dao<ItemInstanceComponentLink, Integer>) DatabaseMechanics.getInstance().getDao(ItemInstanceComponentLink.class);
			
			try {
				instanceLinkDao.create(instanceLink);
				instanceLinkDao.update(instanceLink);
				instanceLinkDao.refresh(instanceLink);
				
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
			
			instanceLink.getDataDao().addAll(createComponentData(instanceLink, baseLink.getComponentId(), item));
			
			ItemComponent component = ComponentFactory.create(baseLink, instanceLink);
			item.addComponent(component);
			component.setItem(item);
		}
		
		ItemInstanceRegistry.put(item);
		
		return item;
	}
	
	public static ArrayList<ItemInstanceComponentData> createComponentData(ItemInstanceComponentLink instanceLink, Integer componentId, ItemInstance item){
		Class<? extends ItemComponent> c = ItemComponentRegistry.getComponentClass(componentId);
		ArrayList<ItemInstanceComponentData> ret = Lists.newArrayList();
		
		@SuppressWarnings("unchecked")
		Dao<ItemInstanceComponentData, Integer> dao = (Dao<ItemInstanceComponentData, Integer>) DatabaseMechanics.getInstance().getDao(ItemInstanceComponentData.class);
		
		for (Field f : c.getFields()){
			if (!fieldIsLocal(f)) continue;
			ItemInstanceComponentData dataEntry = new ItemInstanceComponentData();
			dataEntry.setFieldName(f.getName());
			
			String typeName = f.getType().getTypeName();
			
			if (typeName.contains(".")){
				String[] parts = typeName.split("\\.");
				typeName = parts[parts.length - 1];
			}
			
			dataEntry.setFieldType(typeName);
			try {
				f.setAccessible(true);
				Object value = f.get(item);
				dataEntry.setFieldValue(value);
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				dataEntry.setFieldValue(null);
				e1.printStackTrace();
			}
			dataEntry.setLink(instanceLink);
			try {
				dao.create(dataEntry);
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
			
			ret.add(dataEntry);
			
		}
		
		return ret;
	}
	
	private static boolean fieldIsLocal(Field f){
		return (f.getAnnotation(LocalProperty.class) != null);
	}
}
