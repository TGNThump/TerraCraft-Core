package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemComponentDataEntry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentLink;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.LocalProperty;

import com.google.common.collect.Lists;

public abstract class ItemComponent {
	
	private ItemInstance item;
	
	private Integer baseLinkId;
	private Integer instanceLinkId;
	
	private ItemInstanceComponentLink instanceLink;
	
	public abstract String render();
	
	public Integer getBaseLinkId() {
		return baseLinkId;
	}
	
	public void setBaseLinkId(Integer baseLinkId) {
		this.baseLinkId = baseLinkId;
	}
	
	public Integer getInstanceLinkId() {
		return instanceLinkId;
	}
	
	public void setInstanceLinkId(Integer instanceLinkId) {
		this.instanceLinkId = instanceLinkId;
	}

	public ItemInstance getItem() {
		return item;
	}

	public void setItem(ItemInstance item) {
		this.item = item;
	}

	
	public ItemInstanceComponentLink getInstanceLink() {
		return instanceLink;
	}

	
	public void setInstanceLink(ItemInstanceComponentLink instanceLink) {
		this.instanceLink = instanceLink;
	}

	public void upload() throws SQLException {
		HashMap<String, Field> fields = new HashMap<>();
		ArrayList<String> doneFieldNames = Lists.newArrayList();
		instanceLink.getDataDao().refreshCollection();
		List<ItemComponentDataEntry> data = instanceLink.getData();	
		
		Class<? extends ItemComponent> c = getClass();
		
		for (Field f : c.getFields()){
			if (!fieldIsLocal(f)) continue;
			fields.put(f.getName(),f);
		}
		
		for (ItemComponentDataEntry d : data){
			if (!fields.containsKey(d.getFieldName())) continue;
			try {
				Field f = fields.get(d.getFieldName());
				if (!(d instanceof ItemInstanceComponentData)) continue;
				f.setAccessible(true);
				Object value = f.get(this);
				d.setFieldValue(value);
				((ItemInstanceComponentData) d).setLink(instanceLink);
				instanceLink.getDataDao().update((ItemInstanceComponentData) d);
				doneFieldNames.add(f.getName());
			} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		for (Field f : fields.values()){
			if (doneFieldNames.contains(f.getName())) continue;
			try {
				ItemInstanceComponentData d = new ItemInstanceComponentData();
				f.setAccessible(true);
				d.setFieldName(f.getName());
				
				String typeName = f.getType().getTypeName();
				
				if (typeName.contains(".")){
					String[] parts = typeName.split("\\.");
					typeName = parts[parts.length - 1];
				}
				
				d.setFieldType(typeName);
				Object value = f.get(this);
				d.setFieldValue(value);
				d.setLink(instanceLink);
				instanceLink.getDataDao().add(d);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void download() {
		
	}
	
	private static boolean fieldIsLocal(Field f){
		return (f.getAnnotation(LocalProperty.class) != null);
	}

}
