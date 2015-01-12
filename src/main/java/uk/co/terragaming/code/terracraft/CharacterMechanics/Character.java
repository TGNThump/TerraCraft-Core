package uk.co.terragaming.code.terracraft.CharacterMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

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
			TerraLogger.error("Cannot retrieve Items from Database");
			e.printStackTrace();
		}
	}
	
	public void uploadData(){
//		Account account = getAccount();
//		Player player = TerraCraft.Server().getPlayer(account.getPlayerUUID());
//		
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

	public int getAttribute(String attribute) {
		return this.attributes.get(attribute);
	}

	public void setAttribute(CharacterAttribute strength, Integer value) {
		this.attributes.put(strength, value);
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
