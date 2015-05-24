package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.MaterialPersister;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemInstances")
public class ItemInstance {

	@DatabaseField(generatedId = true, columnName = "itemInstanceId")
	private Integer id;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "itemId")
	private Item item;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "charId")
	private Character character;
	
	@DatabaseField(canBeNull = true)
	private Integer slotId;
	
	@DatabaseField(canBeNull = true)
	private String name;
	
	@DatabaseField(canBeNull = true, persisterClass = MaterialPersister.class)
	private Material material;
	
	@DatabaseField(canBeNull = true)
	private ItemQuality quality;
	
	@DatabaseField(canBeNull = true, columnName = "bound")
	private ItemBinding binding;
	
	@DatabaseField(canBeNull = true)
	private Integer minModDamage;
	
	@DatabaseField(canBeNull = true)
	private Integer maxModDamage;
	
	@DatabaseField(canBeNull = true)
	private String data;

	@DatabaseField(canBeNull = true)
	private Integer modStrength;
	
	@DatabaseField(canBeNull = true)
	private Integer modAgility;
	
	@DatabaseField(canBeNull = true)
	private Integer modStamina;
	
	@DatabaseField(canBeNull = true)
	private Integer modSpirit;
	
	@DatabaseField(canBeNull = true)
	private Integer modResistance;
	
	@DatabaseField(canBeNull = true)
	private Integer modIntellect;
	
	@DatabaseField(canBeNull = true)
	private Integer modVitality;
	
	@DatabaseField(canBeNull = true)
	private Integer cost;
	
	@DatabaseField(canBeNull = true)
	private Integer curDurability;

	// Getters
	
	public Integer getId() { return id; }
	public Item getItem() { return item; }
	public Character getCharacter() { return character; }
	public Integer getSlotId() { return slotId; }
	public String getName() { return name; }
	public Material getMaterial() { return material; }
	public ItemQuality getQuality() { return quality; }
	public ItemBinding getBinding() { return binding; }
	
	public Integer getMinModDamage() { return minModDamage; }
	public Integer getMaxModDamage() { return maxModDamage; }
	
	public String getData() { return data; }

	public Integer getModStrength() { return modStrength; }
	public Integer getModAgility() { return modAgility; }
	public Integer getModStamina() { return modStamina; }
	public Integer getModSpirit() { return modSpirit; }
	public Integer getModResistance() { return modResistance; }
	public Integer getModIntellect() { return modIntellect; }
	public Integer getModVitality() { return modVitality; }

	public Integer getCost() { return cost; }
	public Integer getCurDurability() { return curDurability; }

	// Setters	
	
	public void setId(Integer id) { this.id = id; }
	public void setItem(Item item) { this.item = item; }
	public void setCharacter(Character character) { this.character = character; }
	public void setSlotId(Integer slotId) { this.slotId = slotId; }
	public void setName(String name) { this.name = name; }
	public void setMaterial(Material material) { this.material = material; }
	public void setQuality(ItemQuality quality) { this.quality = quality; }
	public void setBinding(ItemBinding binding) { this.binding = binding; }

	public void setMinModDamage(Integer minModDamage) { this.minModDamage = minModDamage; }
	public void setMaxModDamage(Integer maxModDamage) { this.maxModDamage = maxModDamage; }

	public void setData(String data) { this.data = data; }

	public void setModStrength(Integer modStrength) { this.modStrength = modStrength; }
	public void setModAgility(Integer modAgility) { this.modAgility = modAgility; }
	public void setModStamina(Integer modStamina) { this.modStamina = modStamina; }
	public void setModSpirit(Integer modSpirit) { this.modSpirit = modSpirit; }
	public void setModResistance(Integer modResistance) { this.modResistance = modResistance; }
	public void setModIntellect(Integer modIntellect) { this.modIntellect = modIntellect; }
	public void setModVitality(Integer modVitality) { this.modVitality = modVitality; }
	
	public void setCost(Integer cost) { this.cost = cost; }
	public void setCurDurability(Integer curDurability) { this.curDurability = curDurability; }

	// Init
	
	public ItemInstance(){}
	
	// Override Methods
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null || other.getClass() != getClass()) return false;
		return id.equals(((ItemInstance) other).getId());
	}
	
	@Override
	public String toString(){
		return "ItemInstance[ID: " + getId() + ", Name: " + getName() + ", Item: " + getItem().toString() + "]";
	}
}
