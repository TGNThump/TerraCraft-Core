package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemClass;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.MaterialPersister;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItems")
public class Item {
	
	@DatabaseField(generatedId = true, columnName = "itemId")
	private Integer id;

	@DatabaseField(canBeNull = true)
	private String name;

	@DatabaseField(canBeNull = true, persisterClass = MaterialPersister.class)
	private Material material;

	@DatabaseField(canBeNull = true)
	private ItemQuality quality;

	@DatabaseField(canBeNull = true, columnName = "bindType")
	private ItemBindType bindType;

	@DatabaseField(canBeNull = true, columnName = "class")
	private ItemClass itemClass;

	@DatabaseField(canBeNull = true)
	private String type;

	@DatabaseField(canBeNull = false)
	private Integer requiredStrength;

	@DatabaseField(canBeNull = false)
	private Integer requiredAgility;

	@DatabaseField(canBeNull = false)
	private Integer requiredStamina;

	@DatabaseField(canBeNull = false)
	private Integer requiredSpirit;

	@DatabaseField(canBeNull = false)
	private Integer requiredResistance;

	@DatabaseField(canBeNull = false)
	private Integer requiredIntellect;

	@DatabaseField(canBeNull = false)
	private Integer requiredVitality;

	@DatabaseField(canBeNull = false)
	private Integer requiredRank;

	@DatabaseField(canBeNull = false)
	private Integer modStrength;

	@DatabaseField(canBeNull = false)
	private Integer modAgility;

	@DatabaseField(canBeNull = false)
	private Integer modStamina;

	@DatabaseField(canBeNull = false)
	private Integer modSpirit;

	@DatabaseField(canBeNull = false)
	private Integer modResistance;

	@DatabaseField(canBeNull = false)
	private Integer modIntellect;

	@DatabaseField(canBeNull = false)
	private Integer modVitality;

	@DatabaseField(canBeNull = false)
	private Integer manaCost;

	@DatabaseField(canBeNull = false)
	private Integer durabilityCost;

	@DatabaseField(canBeNull = false)
	private Integer hungerCost;

	@DatabaseField(canBeNull = false)
	private Integer healthCost;

	@DatabaseField(canBeNull = false)
	private Integer cost;

	@DatabaseField(canBeNull = false)
	private Integer maxDurability;

	// Getters
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Material getMaterial() { return material; }
	public ItemQuality getQuality() { return quality; }
	public ItemBindType getBindType() { return bindType; }
	public ItemClass getItemClass() { return itemClass; }
	public String getType() { return type; }

	public Integer getRequiredStrength() { return requiredStrength; }
	public Integer getRequiredAgility() { return requiredAgility; }
	public Integer getRequiredStamina() { return requiredStamina; }
	public Integer getRequiredSpirit() { return requiredSpirit; }
	public Integer getRequiredResistance() { return requiredResistance; }
	public Integer getRequiredIntellect() { return requiredIntellect; }
	public Integer getRequiredVitality() { return requiredVitality; }
	public Integer getRequiredRank() { return requiredRank; }

	public Integer getModStrength() { return modStrength; }
	public Integer getModAgility() { return modAgility; }
	public Integer getModStamina() { return modStamina; }
	public Integer getModSpirit() { return modSpirit; }
	public Integer getModResistance() { return modResistance; }
	public Integer getModIntellect() { return modIntellect; }
	public Integer getModVitality() { return modVitality; }
	
	public Integer getManaCost() { return manaCost; }
	public Integer getDurabilityCost() { return durabilityCost; }
	public Integer getHungerCost() { return hungerCost; }
	public Integer getHealthCost() { return healthCost; }
	public Integer getCost() { return cost; }
	public Integer getMaxDurability() { return maxDurability; }

	// Setters
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setMaterial(Material material) { this.material = material; }
	public void setQuality(ItemQuality quality) { this.quality = quality; }
	public void setBindType(ItemBindType bindType) { this.bindType = bindType; }
	public void setItemClass(ItemClass itemClass) { this.itemClass = itemClass; }
	public void setType(String type) { this.type = type; }

	public void setRequiredStrength(Integer requiredStrength) { this.requiredStrength = requiredStrength; }
	public void setRequiredAgility(Integer requiredAgility) { this.requiredAgility = requiredAgility; }
	public void setRequiredStamina(Integer requiredStamina) { this.requiredStamina = requiredStamina; }
	public void setRequiredSpirit(Integer requiredSpirit) { this.requiredSpirit = requiredSpirit; }
	public void setRequiredResistance(Integer requiredResistance) { this.requiredResistance = requiredResistance; }
	public void setRequiredIntellect(Integer requiredIntellect) { this.requiredIntellect = requiredIntellect; }
	public void setRequiredVitality(Integer requiredVitality) { this.requiredVitality = requiredVitality; }
	public void setRequiredRank(Integer requiredRank) { this.requiredRank = requiredRank; }

	public void setModStrength(Integer modStrength) { this.modStrength = modStrength; }
	public void setModAgility(Integer modAgility) { this.modAgility = modAgility; }
	public void setModStamina(Integer modStamina) { this.modStamina = modStamina; }
	public void setModSpirit(Integer modSpirit) { this.modSpirit = modSpirit; }
	public void setModResistance(Integer modResistance) { this.modResistance = modResistance; }
	public void setModIntellect(Integer modIntellect) { this.modIntellect = modIntellect; }
	public void setModVitality(Integer modVitality) { this.modVitality = modVitality; }
	
	public void setManaCost(Integer manaCost) { this.manaCost = manaCost; }
	public void setDurabilityCost(Integer durabilityCost) { this.durabilityCost = durabilityCost; }
	public void setHungerCost(Integer hungerCost) { this.hungerCost = hungerCost; }
	public void setHealthCost(Integer healthCost) { this.healthCost = healthCost; }
	public void setCost(Integer cost) { this.cost = cost; }
	public void setMaxDurability(Integer maxDurability) { this.maxDurability = maxDurability; }
	
	// Init

	public Item(){}
	
	// Methods
	
	public ItemInstance createInstance(){
		ItemInstance instance = new ItemInstance();
		instance.setItem(this);
		instance.setCurDurability(this.getMaxDurability());
		
		
		
		return instance;
	}

	// Override Methods

	@Override
	public int hashCode(){
		return id.hashCode();
	}

	@Override
	public boolean equals(Object other){
		if (other == null || other.getClass() != getClass()) return false;
		return id.equals(((Item) other).getId());
	}

	@Override
	public String toString(){
		return "Item[ID: " + getId() + ", Name: " + getName() + "]";
	}
}