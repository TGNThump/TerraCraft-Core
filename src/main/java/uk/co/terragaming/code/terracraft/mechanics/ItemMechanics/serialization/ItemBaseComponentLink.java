package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization;

import java.util.ArrayList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemBase;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemBaseComponents")
public class ItemBaseComponentLink extends ItemComponentLink{
	
	@DatabaseField(generatedId = true, canBeNull = false)
	private Integer id;
	
	@DatabaseField(foreign = true, canBeNull = false, columnName = "baseId")
	private ItemBase itemBase;
	
	@DatabaseField(foreign = true, canBeNull = false, columnName = "componentId")
	private ItemComponentData componentData;
	
	@ForeignCollectionField(eager = true, columnName = "linkId", foreignFieldName = "link")
	private ForeignCollection<ItemBaseComponentData> data;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getComponentId() {
		return componentData.getId();
	}

	@Override
	public ArrayList<ItemComponentDataEntry> getData() {
		ArrayList<ItemComponentDataEntry> ret = Lists.newArrayList();
		for (ItemBaseComponentData dataEntry : data){
			ret.add(dataEntry);
		}
		return ret;
	}

	@Override
	public ItemComponentData getComponentData() {
		return componentData;
	}
}
