package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Database;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class Item {
	private final int id;
	private String name;
	private Material material;
	private String quality;
	private String bind;
	private String itemClass;
	private String type;
	private int minBaseDamage;
	private int maxBaseDamage;
	private String data;
	
	private HashMap<String, Integer> requiredAttributes = new HashMap<String, Integer>(); 	// [AttributeName, Required Value] - Includes 'Rank' Requirement
	private HashMap<String, Integer> moddedAttributes = new HashMap<String, Integer>();		// [AttributeName, Value Modifier]
	
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
			Connection connection = Database.getInstance().getConnection();

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

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String string) {
		this.bind = string;
	}

	public String getItemClass() {
		return itemClass;
	}

	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
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

	public HashMap<String, Integer> getRequiredAttributes() {
		return requiredAttributes;
	}

	public Integer getRequiredAttribute(String attribute){
		return requiredAttributes.get(attribute);
	}
	
	public void setRequiredAttribute(String attribute, Integer value){
		this.requiredAttributes.put(attribute, value);
	}

	public HashMap<String, Integer> getModdedAttributes() {
		return moddedAttributes;
	}

	public Integer getAttributeModifier(String attribute){
		return moddedAttributes.get(attribute);
	}
	
	public void setAttributeModifier(String attribute, Integer value){
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
