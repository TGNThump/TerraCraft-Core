package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcClasses")
public class CharacterClass {
	
	@DatabaseField(generatedId = true, columnName = "classId")
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String description;
	
	@DatabaseField(canBeNull = false)
	private Integer baseStrength;
	
	@DatabaseField(canBeNull = false)
	private Integer baseAgility;
	
	@DatabaseField(canBeNull = false)
	private Integer baseStamina;
	
	@DatabaseField(canBeNull = false)
	private Integer baseSpirit;
	
	@DatabaseField(canBeNull = false)
	private Integer baseResistance;
	
	@DatabaseField(canBeNull = false)
	private Integer baseIntellect;
	
	@DatabaseField(canBeNull = false)
	private Integer baseVitality;
	
	// Getters
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Integer getBaseStrength() {
		return baseStrength;
	}
	
	public Integer getBaseAgility() {
		return baseAgility;
	}
	
	public Integer getBaseStamina() {
		return baseStamina;
	}
	
	public Integer getBaseSpirit() {
		return baseSpirit;
	}
	
	public Integer getBaseResistance() {
		return baseResistance;
	}
	
	public Integer getBaseIntellect() {
		return baseIntellect;
	}
	
	public Integer getBaseVitality() {
		return baseVitality;
	}
	
	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setBaseStrength(Integer attr) {
		baseStrength = attr;
	}
	
	public void setBaseAgility(Integer attr) {
		baseAgility = attr;
	}
	
	public void setBaseStamina(Integer attr) {
		baseStamina = attr;
	}
	
	public void setBaseSpirit(Integer attr) {
		baseSpirit = attr;
	}
	
	public void setBaseResistance(Integer attr) {
		baseResistance = attr;
	}
	
	public void setBaseIntellect(Integer attr) {
		baseIntellect = attr;
	}
	
	public void setBaseVitality(Integer attr) {
		baseVitality = attr;
	}
	
	// Init
	
	public CharacterClass() {}
	
	// Override Methods
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		return id.equals(((CharacterClass) other).getId());
	}
	
	@Override
	public String toString() {
		return "CharacterClass[ID: " + getId() + ", Name: " + getName() + "]";
	}
}
