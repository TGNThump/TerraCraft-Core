package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.MaterialPersister;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemBaseComponentLink;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemBase")
public class ItemBase {
	
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "itemId")
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false, persisterClass = MaterialPersister.class)
	private Material icon;
	
	@DatabaseField(canBeNull = false, columnName = "type")
	private String typeName;
	
	@DatabaseField(canBeNull = false, columnName = "class")
	private String className;
	
	@ForeignCollectionField(eager = true, columnName = "baseId", foreignFieldName = "itemBase")
	private ForeignCollection<ItemBaseComponentLink> componentLinks;
	
	// Non Database Variables
	
	
	
	// Methods
	
	
	
	// Getters and Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Material getIcon() {
		return icon;
	}
	
	public void setIcon(Material icon) {
		this.icon = icon;
	}
	
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	public ForeignCollection<ItemBaseComponentLink> getComponentLinks(){
		return componentLinks;
	}
}
