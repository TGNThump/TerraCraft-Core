package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;

public abstract class Itemold {
	
	private Integer id;
	
	private String name;
	private String prefix;
	private String suffix;
	
	private Material icon;
	
	private ItemQuality quality;
	
	private ItemBindType bindType;
	
	private Integer requiredStrength;
	private Integer requiredAgility;
	private Integer requiredStamina;
	private Integer requiredSpirit;
	private Integer requiredResistance;
	private Integer requiredIntellect;
	private Integer requiredVitality;
	private Integer requiredRank;
	
	private Integer durabilityCost;
	private Integer healthCost;
	private Integer hungerCost;
	private Integer moneyCost;
	
	public Itemold() {
		name = "";
		prefix = "";
		suffix = "";
		icon = Material.STONE;
		quality = ItemQuality.COMMON;
		bindType = ItemBindType.NONE;
		
		requiredStrength = 0;
		requiredAgility = 0;
		requiredStamina = 0;
		requiredSpirit = 0;
		requiredResistance = 0;
		requiredIntellect = 0;
		requiredVitality = 0;
		requiredRank = 0;
		
		durabilityCost = 0;
		healthCost = 0;
		hungerCost = 0;
		moneyCost = 0;
	}
	
	public String getStatDescription(){
		return "";
	}
	
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
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public Material getIcon() {
		return icon;
	}
	
	public void setIcon(Material icon) {
		this.icon = icon;
	}
	
	public ItemQuality getQuality() {
		return quality;
	}
	
	public void setQuality(ItemQuality quality) {
		this.quality = quality;
	}
	
	public ItemBindType getBindType() {
		return bindType;
	}
	
	public void setBindType(ItemBindType bindType) {
		this.bindType = bindType;
	}
	
	public Integer getRequiredStrength() {
		return requiredStrength;
	}
	
	public void setRequiredStrength(Integer requiredStrength) {
		this.requiredStrength = requiredStrength;
	}
	
	public Integer getRequiredAgility() {
		return requiredAgility;
	}
	
	public void setRequiredAgility(Integer requiredAgility) {
		this.requiredAgility = requiredAgility;
	}
	
	public Integer getRequiredStamina() {
		return requiredStamina;
	}
	
	public void setRequiredStamina(Integer requiredStamina) {
		this.requiredStamina = requiredStamina;
	}
	
	public Integer getRequiredSpirit() {
		return requiredSpirit;
	}
	
	public void setRequiredSpirit(Integer requiredSpirit) {
		this.requiredSpirit = requiredSpirit;
	}
	
	public Integer getRequiredResistance() {
		return requiredResistance;
	}
	
	public void setRequiredResistance(Integer requiredResistance) {
		this.requiredResistance = requiredResistance;
	}
	
	public Integer getRequiredIntellect() {
		return requiredIntellect;
	}
	
	public void setRequiredIntellect(Integer requiredIntellect) {
		this.requiredIntellect = requiredIntellect;
	}
	
	public Integer getRequiredVitality() {
		return requiredVitality;
	}
	
	public void setRequiredVitality(Integer requiredVitality) {
		this.requiredVitality = requiredVitality;
	}
	
	public Integer getRequiredRank() {
		return requiredRank;
	}
	
	public void setRequiredRank(Integer requiredRank) {
		this.requiredRank = requiredRank;
	}
	
	public Integer getDurabilityCost() {
		return durabilityCost;
	}
	
	public void setDurabilityCost(Integer durabilityCost) {
		this.durabilityCost = durabilityCost;
	}
	
	public Integer getHealthCost() {
		return healthCost;
	}
	
	public void setHealthCost(Integer healthCost) {
		this.healthCost = healthCost;
	}
	
	public Integer getHungerCost() {
		return hungerCost;
	}
	
	public void setHungerCost(Integer hungerCost) {
		this.hungerCost = hungerCost;
	}
	
	public Integer getMoneyCost() {
		return moneyCost;
	}
	
	public void setMoneyCost(Integer moneyCost) {
		this.moneyCost = moneyCost;
	}
}
