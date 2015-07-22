package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemBaseComponentData")
public class ItemBaseComponentData extends ItemComponentDataEntry{
	
	@DatabaseField(generatedId = true, canBeNull = false)
	private Integer id;
	
	@DatabaseField(foreign = true, canBeNull = false, columnName = "linkId")
	private ItemBaseComponentLink link;
	
	@DatabaseField(canBeNull = false)
	private String fieldName;
	
	@DatabaseField(canBeNull = false)
	private String fieldValue;
	
	@DatabaseField(canBeNull = false)
	private String fieldType;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public ItemComponentLink getLink() {
		return link;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getFieldType() {
		return fieldType;
	}

	@Override
	protected String getStrFieldValue() {
		return fieldValue;
	}

	@Override
	protected void setStrFieldValue(String value) {
		fieldValue = value;
	}
}
