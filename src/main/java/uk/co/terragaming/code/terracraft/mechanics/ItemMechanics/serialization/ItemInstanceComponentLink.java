package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization;

import java.util.ArrayList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemInstanceComponents")
public class ItemInstanceComponentLink  extends ItemComponentLink{
	
	@DatabaseField(generatedId = true, canBeNull = false)
	private Integer id;
	
	@DatabaseField(foreign = true, canBeNull = false, columnName = "instanceId")
	private ItemInstance itemInstance;
	
	@DatabaseField(foreign = true, canBeNull = false, columnName = "componentId")
	private ItemComponentData componentData;
	
	@ForeignCollectionField(eager = true, columnName = "linkId", foreignFieldName = "link")
	private ForeignCollection<ItemInstanceComponentData> data;

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
		for (ItemInstanceComponentData dataEntry : data){
			ret.add(dataEntry);
		}
		return ret;
	}
	
	public ItemInstance getItemInstance(){
		return itemInstance;
	}

	
	public ItemComponentData getComponentData() {
		return componentData;
	}

	
	public void setComponentData(ItemComponentData componentData) {
		this.componentData = componentData;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public void setItemInstance(ItemInstance itemInstance) {
		this.itemInstance = itemInstance;
	}
	
	public ForeignCollection<ItemInstanceComponentData> getDataDao(){
		return data;
	}

	
	public void setData(ForeignCollection<ItemInstanceComponentData> data) {
		this.data = data;
	}
}
