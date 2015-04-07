package uk.co.terragaming.code.terracraft.CharacterMechanics.InventoryMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.enums.CharacterAttribute;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.utils.StringTools;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class InventoryMechanics {

	private static DatabaseMechanics databaseMechanics;
	private static ItemMechanics itemMechanics;
	private static UUID uuid;
	
	public static void initialize(){
		databaseMechanics = DatabaseMechanics.getInstance();
		itemMechanics = ItemMechanics.getInstance();
		uuid = TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft");
	}
	
	public static void downloadCharInventory(Account account, Character character){
		try {
			ItemInstanceRegistry itemInstanceRegistry = itemMechanics.getItemInstanceRegistry();
			
			Connection connection = databaseMechanics.getConnection();
			String sql = "SELECT * FROM tcItemInstances WHERE charId = ?";
			PreparedStatement query = connection.prepareStatement(sql);
					
			query.setInt(1, character.getId());
			query.executeQuery();
			
			ResultSet results = query.getResultSet();
			results.beforeFirst();
			
			while(results.next()){
				Integer itemInstanceId = results.getInt("itemInstanceId");
				ItemInstance item = new ItemInstance(itemInstanceId);
				
				item.setId(itemInstanceId);
				item.setItemId(results.getInt("itemId"));
				item.setOwnerId(results.getInt("charId"));
				item.setSlotid(results.getInt("slotId"));
				item.setName(results.getString("name"));
				item.setMaterial(Material.getMaterial(results.getString("material")));
				
				item.setQuality(ItemQuality.getQuality(results.getString("quality")));
				
				item.setBinding(ItemBinding.getBinding(results.getString("bound")));
				
				item.setMinDamageMod(results.getInt("minModDamage"));
				item.setMaxDamageMod(results.getInt("maxModDamage"));
				
				item.setData(results.getString("data"));
				
				item.setRawModdedAttribute(CharacterAttribute.STRENGTH, results.getInt("modStrength"));
				item.setRawModdedAttribute(CharacterAttribute.AGILITY, results.getInt("modAgility"));
				item.setRawModdedAttribute(CharacterAttribute.STAMINA, results.getInt("modStamina"));
				item.setRawModdedAttribute(CharacterAttribute.SPIRIT, results.getInt("modSpirit"));
				item.setRawModdedAttribute(CharacterAttribute.RESISTANCE, results.getInt("modResistance"));
				item.setRawModdedAttribute(CharacterAttribute.INTELLECT, results.getInt("modIntellect"));
				item.setRawModdedAttribute(CharacterAttribute.VITALITY, results.getInt("modVitality"));
								
				item.setValue(results.getInt("cost"));
				item.setDurability(results.getInt("curDurability"));
				
				itemInstanceRegistry.addItemInstance(item, character.getId());
			}
			
			connection.close();
			
		} catch (SQLException e){
			TerraLogger.error("Failed to Download Character Inventory for " + account.getTerraTag() + " (AccountId: " + account.getId() + ", CharacterId: " + character.getId() + ")");
			e.printStackTrace();
		}
	}
	
	public static void applyCharInventory(Account account, Character character){
		Player player = account.getPlayer();
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		
		ItemInstance[] items = itemMechanics.getItemInstanceRegistry().getItemInstances(character.getId());
		if (items == null){ return; }
		for (ItemInstance item : items){
			inventory.setItem(item.getSlotid(), item.getItemStack());
		}
	}
	
	public static void uploadCharInventory(Account account, Character character) throws SQLException{
		ItemInstanceRegistry itemInstanceRegistry = itemMechanics.getItemInstanceRegistry();

		Player player = account.getPlayer();
		PlayerInventory inventory = player.getInventory();
		
		Connection connection = databaseMechanics.getConnection();
		String sql = "UPDATE tcItemInstances SET charId = NULL, slotId = NULL WHERE charId = ?";
		PreparedStatement query = connection.prepareStatement(sql);
		
		query.setInt(1, character.getId());
		query.executeUpdate();

		sql = "UPDATE tcItemInstances SET " +
				"charId = ?, " +
				"slotId = ?, " +
				"name = ?, " +
				"material = ?, " +
				"quality = ?, " +
				"bound = ?, " +
				"minModDamage = ?, " +
				"maxModDamage = ?, " +
				"data = ?, " +
				"modStrength = ?, " +
				"modAgility = ?, " +
				"modStamina = ?, " +
				"modSpirit = ?, " +
				"modResistance = ?, " +
				"modIntellect = ?, " +
				"modVitality = ?, " +
				"cost = ?, " +
				"curDurability = ? " +
				"WHERE itemInstanceId = ?";
	
	query = connection.prepareStatement(sql);

	int i = 0;
	for(ItemStack is : inventory.getContents()){
		if (is == null){ i++; continue; }
		
		ItemInstance itemInstance = itemMechanics.getItemInstance(is);
		if (itemInstance == null){ continue; }
		
		// charId
		query.setInt(1, character.getId());
		// slotId
		query.setInt(2, i);
		// name
		query.setString(3, itemInstance.getName());
		// material
		query.setString(4, itemInstance.getMaterial().toString());
		// quality
		query.setString(5, StringTools.toNormalCase(itemInstance.getQuality().toString()));
		// bound
		query.setString(6, StringTools.toNormalCase(itemInstance.getBinding().toString()));
		// minModDamage
		query.setInt(7, itemInstance.getMinDamageMod());
		// maxModDamage
		query.setInt(8, itemInstance.getMaxDamageMod());
		// data
		query.setString(9, itemInstance.getData());
		// modStrength
		query.setInt(10, itemInstance.getRawModdedAttribute(CharacterAttribute.STRENGTH));
		// modAgility
		query.setInt(11, itemInstance.getRawModdedAttribute(CharacterAttribute.AGILITY));
		// modStamina
		query.setInt(12, itemInstance.getRawModdedAttribute(CharacterAttribute.STAMINA));
		// modSpirit
		query.setInt(13, itemInstance.getRawModdedAttribute(CharacterAttribute.SPIRIT));
		// modResistance
		query.setInt(14, itemInstance.getRawModdedAttribute(CharacterAttribute.RESISTANCE));
		// modIntellect
		query.setInt(15, itemInstance.getRawModdedAttribute(CharacterAttribute.INTELLECT));
		// modVitality
		query.setInt(16, itemInstance.getRawModdedAttribute(CharacterAttribute.VITALITY));
		// cost
		query.setInt(17, itemInstance.getValue());
		// curDurability
		query.setInt(18, itemInstance.getDurability());
		
		query.setInt(19, itemInstance.getId());
		query.addBatch();
		
		i++;
	}
	query.executeBatch();
	connection.close();
	
	itemInstanceRegistry.removeCharItemInstances(character.getId());

	}
}
