package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.Attribute;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemClass;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.MaterialPersister;
import uk.co.terragaming.code.terracraft.utils.AttributeUtil;
import uk.co.terragaming.code.terracraft.utils.CustomItem;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.comphenix.attribute.AttributeStorage;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemInstances")
public class ItemInstance {

	@DatabaseField(generatedId = true, columnName = "itemInstanceId")
	private Integer id;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "itemId")
	private Item item;
	
	@DatabaseField(canBeNull = true, columnName = "charId", foreign = true)
	private Character character;
	
	@DatabaseField(canBeNull = true)
	private Integer slotId;
	
	@DatabaseField(canBeNull = false)
	private Integer count = 1;
	
	@DatabaseField(canBeNull = true)
	private String name;
	
	@DatabaseField(canBeNull = true, persisterClass = MaterialPersister.class)
	private Material material;
	
	@DatabaseField(canBeNull = true)
	private ItemQuality quality;
	
	@DatabaseField(canBeNull = true, columnName = "bound")
	private ItemBinding binding;
	
	@DatabaseField(canBeNull = false)
	private Integer minModDamage = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer maxModDamage = 0;
	
	@DatabaseField(canBeNull = true)
	private String data;

	@DatabaseField(canBeNull = false)
	private Integer modStrength = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer modAgility = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer modStamina = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer modSpirit = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer modResistance = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer modIntellect = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer modVitality = 0;
	
	@DatabaseField(canBeNull = true)
	private Integer cost;
	
	@DatabaseField(canBeNull = false)
	private Integer curDurability;

	// Getters
	
	public Integer getId() { return id; }
	public Item getItem() { return item; }
	public Character getCharacter() { return character; }
	public Integer getSlotId() { return slotId; }
	public Integer getCount() { return count; }
	
	public String getName() {
		if (name == null) return getItem().getName();
		return name;
	}
	
	public String getColouredName(){
		return Txt.parse(ItemQuality.getChatColor(getQuality()) + "<bold>" + getName() + "<r>");
	}
	
	public Material getMaterial() {
		if (material == null) return getItem().getMaterial();
		return material;
	}
	
	public ItemQuality getQuality() {
		if (quality == null) return getItem().getQuality();
		return quality;
	}
	
	public ItemBinding getBinding() {
		if (binding == null) return ItemBinding.NONE;
		return binding;
	}
	
	public Integer getMinModDamage() { return minModDamage; }
	public Integer getMaxModDamage() { return maxModDamage; }
	
	public Integer getMinDamage(){
		return getMinModDamage() + getItem().getMinBaseDamage();
	}
	
	public Integer getMaxDamage(){
		return getMaxModDamage() + getItem().getMaxBaseDamage();
	}
	
	public String getData() {
		if (data == null) return getItem().getData();
		return data;
	}

	public Integer getRawModStrength() { return modStrength; }
	public Integer getRawModAgility() { return modAgility; }
	public Integer getRawModStamina() { return modStamina; }
	public Integer getRawModSpirit() { return modSpirit; }
	public Integer getRawModResistance() { return modResistance; }
	public Integer getRawModIntellect() { return modIntellect; }
	public Integer getRawModVitality() { return modVitality; }
	
	public Integer getModStrength() { return modStrength + getItem().getModStrength(); }
	public Integer getModAgility() { return modAgility + getItem().getModAgility(); }
	public Integer getModStamina() { return modStamina + getItem().getModStamina(); }
	public Integer getModSpirit() { return modSpirit + getItem().getModSpirit(); }
	public Integer getModResistance() { return modResistance + getItem().getModResistance(); }
	public Integer getModIntellect() { return modIntellect + getItem().getModIntellect(); }
	public Integer getModVitality() { return modVitality + getItem().getModVitality(); }

	public Integer getCost() {
		if (cost == null) return getItem().getCost();
		return cost;
	}
	
	public Integer getCurDurability() { return curDurability; }

	// Setters	
	
	public void setId(Integer id) { this.id = id; }
	public void setItem(Item item) { this.item = item; }
	public void setCharacter(Character character) { this.character = character; }
	public void setSlotId(Integer slotId) { this.slotId = slotId; }
	public void setCount(Integer count) { this.count = count; }
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
	
	// Methods
	
	public Integer getModAttribute(Attribute attribute){
		switch(attribute){
		case STRENGTH:
			return getModStrength();
		case AGILITY:
			return getModAgility();
		case STAMINA:
			return getModStamina();
		case SPIRIT:
			return getModSpirit();
		case RESISTANCE:
			return getModResistance();
		case INTELLECT:
			return getModIntellect();
		case VITALITY:
			return getModVitality();
		default:
			return 0;
		}
	}
	
	public ItemStack getItemStack(){
		if (getItem().getId() == 0){
			// Minecraft Item...
		} else {
			CustomItem item = new CustomItem(getMaterial());
			
			item.setName(getColouredName());
			item.addLore(ChatColor.GRAY + (getItem().getItemClass().equals(ItemClass.MELEE) || getItem().getItemClass().equals(ItemClass.RANGE) ? Txt.upperCaseFirst(getItem().getItemClass().toString().toLowerCase()) + " Weapon" :  Txt.upperCaseFirst(getItem().getItemClass().toString().toLowerCase())) + "                              " + ChatColor.GOLD + this.getItem().getType().toString());
		
			if (getMaxDamage() > 0){
				item.addLore(ChatColor.DARK_GRAY + "" + getMinDamage() + " - " + getMaxDamage() + " Damage");
			}
			
			for (Attribute attr : Attribute.values()){
				Integer value = getModAttribute(attr);
				if (value == 0) continue;
				item.addLore(ChatColor.GOLD + "" + (value > 0 ? "+" : "") + value + " " + attr.toString());
			}
			
			if (getBinding().equals(ItemBinding.ACCOUNT)){
				item.addLore(ChatColor.GRAY + "Heirloom");
			} else if (getBinding().equals(ItemBinding.CHARACTER)){
				item.addLore(ChatColor.GRAY + "Soulbound");
			}
			
			// Durability is weird... 0 is fully repaired, getMaxDurability() is fully broken.
			double maxDamage  = (double) getMaterial().getMaxDurability();
			double percent 	 = (double) getCurDurability() / 100.0;
			double durability = Math.round((1-percent) * maxDamage);

			item.setDurability((short) (durability));
			
			AttributeStorage storage = AttributeStorage.newTarget(item.getItemStack(), AttributeUtil.computeUUID("TerraGamingNetwork-TerraCraft"));
			
			storage.setData("TCID: " + this.getId());
			
			return storage.getTarget();
		}
		return null;
	}
	
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
