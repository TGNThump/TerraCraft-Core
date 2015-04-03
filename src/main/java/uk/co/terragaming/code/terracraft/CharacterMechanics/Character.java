package uk.co.terragaming.code.terracraft.CharacterMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.enums.CharacterAttribute;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.utils.StringTools;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.comphenix.attribute.AttributeStorage;

public class Character {
	private int id;
	private int accountId;
	private int raceId;

	private String name;
	private HashMap<CharacterAttribute, Integer> attributes = new HashMap<CharacterAttribute, Integer>();

	private int maxHitpoints;
	private int curHitpoints;

	private int maxHunger;
	private int curHunger;

	private int maxMana;
	private int curMana;

	private int curLevel;
	private int curExp;

	private int money;
	private int bankMoney;

	private Location location;

	private DateTime lastLogDate;
	private DateTime createDate;

	private String description;
	private String description_ooc;
	private String notes;
	
	public Account getAccount(){
		return AccountMechanics.getInstance().getRegistry().getAccount(accountId);
	}

	public void setActiveCharacter(){
		Account account = getAccount();
		UUID uuid = account.getPlayerUUID();
		Player player = TerraCraft.Server().getPlayer(uuid);
				
		downloadInventoryData();
		
		player.getInventory().clear();
		player.setHealth(this.getCurHitpoints());
		player.setMaxHealth(this.getMaxHitpoints());
		player.setExp(this.getCurExp());
		player.setLevel(this.getCurLevel());
		player.setFoodLevel(this.getCurHunger());
		player.teleport(getLocation());
		player.setCustomName(getName());
		
		for (ItemInstance item : ItemMechanics.getInstance().getItemInstanceRegistry().getItemInstances(getId())){
			player.getInventory().setItem(item.getSlotid(), item.getItemStack());
		}
	}
	
	public void downloadInventoryData(){
		try {
			Connection connection = DatabaseMechanics.getInstance().getConnection();
			
			PreparedStatement query = connection.prepareStatement("SELECT * FROM tcItemInstances WHERE charId = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			query.setInt(1, this.getId());
			query.executeQuery();
			ResultSet results = query.getResultSet();
			results.beforeFirst();
			
			while(results.next()){
				ItemInstance item = new ItemInstance(results.getInt("itemInstanceId"));
				item.setId(results.getInt("itemInstanceId"));
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

				
				ItemMechanics.getInstance().getItemInstanceRegistry().addItemInstance(item, getId());
			}
			
			connection.close();

		} catch (Exception e){
			TerraLogger.error("Cannot retrieve Inventory Data from Database");
			e.printStackTrace();
		}
	}
	

	public void uploadData() {
		TerraLogger.debug("uploadData();");
		try {
			uploadInventoryData();
			
			Connection connection = DatabaseMechanics.getInstance().getConnection();
			String SQL = "UPDATE tcCharacters SET " +
						"raceId = ?, " +
						"name = ?, " +
						"attrStrength = ?, " +
						"attrAgility = ?, " +
						"attrStamina = ?, " +
						"attrSpirit = ?, " +
						"attrResistance = ?, " +
						"attrIntellect = ?, " +
						"attrVitality = ?, " +
						"maxHitpoints = ?, " +
						"curHitpoints = ?, " +
						"maxHunger = ?, " +
						"curHunger = ?, " +
						"maxMana = ?, " +
						"curMana = ?, " +
						"curLevel = ?, " +
						"curExp = ?, " +
						"money = ?, " +
						"bankMoney = ?, " +
						"locWorld = ?, " +
						"locX = ?, " +
						"locY = ?, " +
						"locZ = ?, " +
						"locPitch = ?, " +
						"locYaw = ?, " +
						"lastLoginDate = ?, " +
						"createDate = ?, " +
						"description = ?, " +
						"description_ooc = ?, " +
						"notes = ? " +
						"WHERE characterId = ?";
		
			PreparedStatement query = connection.prepareStatement(SQL);
			
			// raceId
			query.setInt(1, this.getRaceId());
			// name
			query.setString(2, this.getName());
			// attrStrength
			query.setInt(3, this.getAttribute(CharacterAttribute.STRENGTH));
			// attrAgility
			query.setInt(4, this.getAttribute(CharacterAttribute.AGILITY));
			// attrStamina
			query.setInt(5, this.getAttribute(CharacterAttribute.STAMINA));
			// attrSpirit
			query.setInt(6, this.getAttribute(CharacterAttribute.SPIRIT));
			// attrResistance
			query.setInt(7, this.getAttribute(CharacterAttribute.RESISTANCE));
			// attrIntellect
			query.setInt(8, this.getAttribute(CharacterAttribute.INTELLECT));
			// attrVitality
			query.setInt(9, this.getAttribute(CharacterAttribute.VITALITY));
			// maxHitpoints
			query.setInt(10, this.getMaxHitpoints());
			// curHitpoints
			query.setInt(11, this.getCurHitpoints());
			// maxHunger
			query.setInt(12, this.getMaxHunger());
			// curHunger
			query.setInt(13, this.getCurHunger());
			// maxMana
			query.setInt(14, this.getMaxMana());
			// curMana
			query.setInt(15, this.getCurMana());
			// curLevel
			query.setInt(16, this.getCurLevel());
			// curExp
			query.setInt(17, this.getCurExp());
			// money
			query.setInt(18, this.getMoney());
			// bankMoney
			query.setInt(19, this.getBankMoney());
			// locWorld
			query.setString(20, this.getLocation().getWorld().getName());
			// locX			
			query.setInt(21, (int) this.getLocation().getX());
			// locY
			query.setInt(22, (int) this.getLocation().getY());
			// locZ
			query.setInt(23, (int) this.getLocation().getZ());
			// locPitch
			query.setInt(24, Math.round(this.getLocation().getPitch()));
			// locYaw
			query.setInt(25, Math.round(this.getLocation().getYaw()));
			// lastLoginDate
			query.setDate(26, new java.sql.Date(((java.util.Date) this.getLastLogDate().toDate()).getTime()));
			// createDate
			query.setDate(27, new java.sql.Date(((java.util.Date) this.getCreateDate().toDate()).getTime()));
			// description
			query.setString(28, this.getDescription());
			// description_ooc
			query.setString(29, this.getDescription_ooc());
			// notes
			query.setString(30, this.getNotes());
			// characterId
			query.setInt(31, this.getId());
			
			query.execute();
			connection.close();
			
			TerraLogger.info("Uploaded Date of " + getAccount().getTerraTag());
			
		} catch (SQLException e) {
			TerraLogger.error("Failed to upload " + getAccount().getTerraTag() + "'s Character Data to CORE....");
			TerraLogger.error("Their character will be rolled back to before they logged in.");
			e.printStackTrace();
		}
	}
	
	public void uploadInventoryData() throws SQLException{
			TerraLogger.debug("uploadInventoryData();");
			Account account = getAccount();
			Player player = TerraCraft.Server().getPlayer(account.getPlayerUUID());
			
			PlayerInventory inv = player.getInventory();
			UUID uuid = TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft");
			
			Connection connection = DatabaseMechanics.getInstance().getConnection();
			String SQL = "UPDATE tcItemInstances SET " +
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
			
			PreparedStatement query = connection.prepareStatement(SQL);

			int i = 0;
			for(ItemStack is : inv.getContents()){
				if (is == null){ i++; continue; }
				
				AttributeStorage storage = AttributeStorage.newTarget(is, uuid);
				String attrData = storage.getData(null);
				 
				if (!attrData.startsWith("TCID: ")) continue;
				Integer itemInstanceID = Integer.parseInt(attrData.substring(6));

				// TerraLogger.debug("ItemInstanceId: " + itemInstanceID + " at SlotId: " + i);
				
				ItemInstance itemInstance = ItemMechanics.getInstance().getItemInstanceRegistry().getItemInstance(itemInstanceID);
				
				// charId
				query.setInt(1, itemInstance.getOwnerId());
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
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAttributes(HashMap<CharacterAttribute, Integer> attributes) {
		this.attributes = attributes;
	}
	
	public int getAttribute(CharacterAttribute attribute){
		return this.attributes.get(attribute);
	}

	public void setAttribute(CharacterAttribute attribute, Integer value) {
		this.attributes.put(attribute, value);
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public void setMaxHitpoints(int maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}

	public int getCurHitpoints() {
		return curHitpoints;
	}

	public void setCurHitpoints(int curHitpoints) {
		this.curHitpoints = curHitpoints;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public void setMaxHunger(int maxHunger) {
		this.maxHunger = maxHunger;
	}

	public int getCurHunger() {
		return curHunger;
	}

	public void setCurHunger(int curHunger) {
		this.curHunger = curHunger;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getCurMana() {
		return curMana;
	}

	public void setCurMana(int curMana) {
		this.curMana = curMana;
	}

	public int getCurLevel() {
		return curLevel;
	}

	public void setCurLevel(int curLevel) {
		this.curLevel = curLevel;
	}

	public int getCurExp() {
		return curExp;
	}

	public void setCurExp(int curExp) {
		this.curExp = curExp;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getBankMoney() {
		return bankMoney;
	}

	public void setBankMoney(int bankMoney) {
		this.bankMoney = bankMoney;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public DateTime getLastLogDate() {
		return lastLogDate;
	}

	public void setLastLogDate(DateTime lastLogDate) {
		this.lastLogDate = lastLogDate;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription_ooc() {
		return description_ooc;
	}

	public void setDescription_ooc(String description_ooc) {
		this.description_ooc = description_ooc;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
