package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.enums.CharacterAttribute;
import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemClass;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.comphenix.attribute.AttributeStorage;

public class ItemMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return false; }
	
	public static ItemMechanics getInstance(){
		return (ItemMechanics) TerraCraft.getMechanic("ItemMechanics");
	}

	// Mechanic Variables
	private ItemRegistry itemRegistry;
	private ItemInstanceRegistry itemInstanceRegistry;
	private UUID uuid;
	
	// Mechanic Methods
	
	public ItemRegistry getItemRegistry(){
		return itemRegistry;
	}
	
	public ItemInstanceRegistry getItemInstanceRegistry(){
		return itemInstanceRegistry;
	}
	
	public ItemInstance getItemInstance(ItemStack is){
		AttributeStorage storage = AttributeStorage.newTarget(is, uuid);
		String attrData = storage.getData(null);
		if (attrData == null) return null;
		if (!attrData.startsWith("TCID: ")) return null;
		Integer itemInstanceID = Integer.parseInt(attrData.substring(6));
		
		ItemInstance itemInstance = itemInstanceRegistry.getItemInstance(itemInstanceID);
		
		if(itemInstance == null) {TerraLogger.debug("Failed to getItemInstance for Item Id: " + itemInstanceID);}
		
		return itemInstance;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		itemRegistry = new ItemRegistry();
		itemInstanceRegistry = new ItemInstanceRegistry();
		uuid = TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft");
		TerraCraft.Server().getPluginManager().registerEvents(new ItemEvents(), TerraCraft.Plugin());		
	}

	@Override
	public void Initialize() {
		try {
			Connection connection = DatabaseMechanics.getInstance().getConnection();
			
			PreparedStatement query = connection.prepareStatement("SELECT * FROM tcItems", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			query.executeQuery();
			ResultSet results = query.getResultSet();
			results.beforeFirst();
			
			while(results.next()){
				Item item = new Item(results.getInt("itemId"), results.getString("name"), Material.getMaterial(results.getString("material")));
				item.setQuality(ItemQuality.getQuality(results.getString("quality")));
				item.setBind(ItemBindType.getBindType(results.getString("bind")));
				item.setItemClass(ItemClass.getItemClass(results.getString("class")));
				item.setType(results.getString("type"));
				item.setData(results.getString("data"));
				
				item.setMinBaseDamage(results.getInt("minBaseDamage"));
				item.setMaxBaseDamage(results.getInt("maxBaseDamage"));
				
				
				item.setRequiredAttribute(CharacterAttribute.STRENGTH, results.getInt("requiredStrength"));
				item.setRequiredAttribute(CharacterAttribute.AGILITY, results.getInt("requiredAgility"));
				item.setRequiredAttribute(CharacterAttribute.STAMINA, results.getInt("requiredStamina"));
				item.setRequiredAttribute(CharacterAttribute.SPIRIT, results.getInt("requiredSpirit"));
				item.setRequiredAttribute(CharacterAttribute.RESISTANCE, results.getInt("requiredResistance"));
				item.setRequiredAttribute(CharacterAttribute.INTELLECT, results.getInt("requiredIntellect"));
				item.setRequiredAttribute(CharacterAttribute.VITALITY, results.getInt("requiredVitality"));
				item.setRequiredAttribute(CharacterAttribute.RANK, results.getInt("requiredRank"));
				
				item.setManaCost(results.getInt("manaCost"));
				item.setDurabilityCost(results.getInt("durabilityCost"));
				item.setHungerCost(results.getInt("hungerCost"));
				item.setHealthCost(results.getInt("healthCost"));
				
				item.setAttributeModifier(CharacterAttribute.STRENGTH, results.getInt("modStrength"));
				item.setAttributeModifier(CharacterAttribute.AGILITY, results.getInt("modAgility"));
				item.setAttributeModifier(CharacterAttribute.STAMINA, results.getInt("modStamina"));
				item.setAttributeModifier(CharacterAttribute.SPIRIT, results.getInt("modSpirit"));
				item.setAttributeModifier(CharacterAttribute.RESISTANCE, results.getInt("modResistance"));
				item.setAttributeModifier(CharacterAttribute.INTELLECT, results.getInt("modIntellect"));
				item.setAttributeModifier(CharacterAttribute.VITALITY, results.getInt("modVitality"));
				
				item.setCost(results.getInt("cost"));
				item.setMaxDurability(results.getInt("maxDurability"));
				
				itemRegistry.addItem(item.getId(), item);
				TerraLogger.info("Downloaded Item Data for '" + item.getName() + "'");
			}
			
			connection.close();

		} catch (Exception e){
			TerraLogger.error("Cannot retrieve Items from Database");
			e.printStackTrace();
		}
	}

	@Override
	public void PostInitialize() {
		
	}

	@Override
	public void PreDenitialize() {
		
	}

	@Override
	public void Denitialize() {
		
	}

	@Override
	public void PostDenitialize() {
		
	}
}