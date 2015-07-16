package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groups")
public class Group {
	
	@DatabaseField(generatedId = true, columnName = "groupId")
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
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
}
