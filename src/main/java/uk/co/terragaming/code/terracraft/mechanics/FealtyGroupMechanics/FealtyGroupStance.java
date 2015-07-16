package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics;

import uk.co.terragaming.code.terracraft.enums.FGroupStance;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "tcFealtyGroupStances")
public class FealtyGroupStance {
	
	@DatabaseField(generatedId = true, columnName = "id")
	private Integer id;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "fealtyGroupId")
	private FealtyGroup group;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "otherGroupId")
	private FealtyGroup target;
	
	@DatabaseField(canBeNull = false)
	private FGroupStance stance;

	// Getters and Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public FealtyGroup getGroup() {
		return group;
	}
	
	public void setGroup(FealtyGroup group) {
		this.group = group;
	}
	
	public FealtyGroup getTarget() {
		return target;
	}
	
	public void setTarget(FealtyGroup target) {
		this.target = target;
	}
	
	public FGroupStance getStance() {
		return stance;
	}
	
	public void setStance(FGroupStance stance) {
		this.stance = stance;
	}
}
