package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.enums.CharacterAttribute;
import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemClass;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class Item {
	private final Integer id;
	private String name;
	private Material material;
	private ItemQuality quality;
	private ItemBindType bind;
	private ItemClass itemClass;
	private String type;
	private int minBaseDamage;
	private int maxBaseDamage;
	private String data;
	
	private HashMap<CharacterAttribute, Integer> requiredAttributes = new HashMap<CharacterAttribute, Integer>(); 	// [AttributeName, Required Value] - Includes 'Rank' Requirement
	private HashMap<CharacterAttribute, Integer> moddedAttributes = new HashMap<CharacterAttribute, Integer>();		// [AttributeName, Value Modifier]
	
	private int manaCost;
	private int healthCost;
	private int durabilityCost;
	private int hungerCost;
	
	private int cost;
	private int maxDurability;
	
	public Item(int id, String name, Material material){
		this.id = id;
		this.setName(name);
		this.setMaterial(material);
	}

	public ItemInstance createInstance(){
		ItemInstance instance = new ItemInstance(getId());
		instance.setDurability(maxDurability);
		
		try{
			Connection connection = DatabaseMechanics.getInstance().getConnection();

				String SQL = "INSERT INTO tcItemInstances (itemId) VALUES (?)";
				
				PreparedStatement query = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				query.setInt(1, id);
				query.executeUpdate();
				
				ResultSet rs = query.getGeneratedKeys();
				rs.first();
				instance.setId(rs.getInt(1));

			connection.close();
		} catch (Exception e){
			e.printStackTrace();
			TerraLogger.debug("Failed to create Item Instance in Database...");
		}
		
		ItemMechanics.getInstance().getItemInstanceRegistry().addItemInstance(instance.getId(), instance);
		return instance;
	}
	
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public ItemQuality getQuality() {
		return quality;
	}

	public void setQuality(ItemQuality itemQuality) {
		this.quality = itemQuality;
	}

	public ItemBindType getBind() {
		return bind;
	}

	public void setBind(ItemBindType itemBindType) {
		this.bind = itemBindType;
	}

	public ItemClass getItemClass() {
		return itemClass;
	}

	public void setItemClass(ItemClass itemClass2) {
		this.itemClass = itemClass2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMinBaseDamage() {
		return minBaseDamage;
	}

	public void setMinBaseDamage(int minBaseDamage) {
		this.minBaseDamage = minBaseDamage;
	}

	public int getMaxBaseDamage() {
		return maxBaseDamage;
	}

	public void setMaxBaseDamage(int maxBaseDamage) {
		this.maxBaseDamage = maxBaseDamage;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HashMap<CharacterAttribute, Integer> getRequiredAttributes() {
		return requiredAttributes;
	}

	public Integer getRequiredAttribute(CharacterAttribute attribute){
		return requiredAttributes.get(attribute);
	}
	
	public void setRequiredAttribute(CharacterAttribute strength, Integer value){
		this.requiredAttributes.put(strength, value);
	}

	public HashMap<CharacterAttribute, Integer> getModdedAttributes() {
		return moddedAttributes;
	}

	public Integer getAttributeModifier(CharacterAttribute attribute){
		return moddedAttributes.get(attribute);
	}
	
	public void setAttributeModifier(CharacterAttribute attribute, Integer value){
		this.moddedAttributes.put(attribute, value);
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getHealthCost() {
		return healthCost;
	}

	public void setHealthCost(int healthCost) {
		this.healthCost = healthCost;
	}

	public int getDurabilityCost() {
		return durabilityCost;
	}

	public void setDurabilityCost(int durabilityCost) {
		this.durabilityCost = durabilityCost;
	}

	public int getHungerCost() {
		return hungerCost;
	}

	public void setHungerCost(int hungerCost) {
		this.hungerCost = hungerCost;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}
}
