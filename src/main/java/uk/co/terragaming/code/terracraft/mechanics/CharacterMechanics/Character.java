package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ContainerData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.CharacterContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ContainerFactory;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcCharacters")
public class Character {
	
	@DatabaseField(generatedId = true, columnName = "characterId")
	private Integer id;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "accountId")
	private Account account;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private Integer attrStrength = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer attrAgility = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer attrStamina = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer attrSpirit = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer attrResistance = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer attrIntellect = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer attrVitality = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer maxHitpoints = 20;
	
	@DatabaseField(canBeNull = false)
	private Integer curHitpoints = 20;
	
	@DatabaseField(canBeNull = false)
	private Integer maxHunger = 100;
	
	@DatabaseField(canBeNull = false)
	private Integer curHunger = 100;
	
	@DatabaseField(canBeNull = false)
	private Integer curSaturation = 100;
	
	@DatabaseField(canBeNull = false)
	private Integer curExhaustion = 100;
	
	@DatabaseField(canBeNull = false)
	private Integer maxMana = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer curMana = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer curLevel = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer curExp = 0;
	
	@DatabaseField(canBeNull = false)
	private Integer money = 100;
	
	@DatabaseField(canBeNull = false)
	private Integer bankMoney = 0;
	
	@DatabaseField(canBeNull = true, useGetSet = true)
	private String locWorld;
	
	@DatabaseField(canBeNull = true, useGetSet = true)
	private Integer locX;
	
	@DatabaseField(canBeNull = true, useGetSet = true)
	private Integer locY;
	
	@DatabaseField(canBeNull = true, useGetSet = true)
	private Integer locZ;
	
	@DatabaseField(canBeNull = true, useGetSet = true)
	private Integer locPitch;
	
	@DatabaseField(canBeNull = true, useGetSet = true)
	private Integer locYaw;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime lastLoginDate;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime createDate;
	
	@DatabaseField(canBeNull = true)
	private String description;
	
	@DatabaseField(canBeNull = true, columnName = "description_ooc")
	private String descriptionOOC;
	
	@DatabaseField(canBeNull = true)
	private String notes;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "containerId")
	private ContainerData containerData;
	
	private Container container;
	
	@ForeignCollectionField(eager = true, columnName = "patronId", foreignFieldName = "patron")
	private ForeignCollection<Character> vassals;
	
	@DatabaseField(canBeNull = true, columnName = "patronId", foreign = true)
	private Character patron;
	
	// Non Database Fields
	
	private Location location = Bukkit.getWorld("TerraCraft_old").getSpawnLocation();
	
	// Getters
	
	public Integer getId() {
		return id;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getAttrStrength() {
		return attrStrength;
	}
	
	public Integer getAttrAgility() {
		return attrAgility;
	}
	
	public Integer getAttrStamina() {
		return attrStamina;
	}
	
	public Integer getAttrSprit() {
		return attrSpirit;
	}
	
	public Integer getAttrResistance() {
		return attrResistance;
	}
	
	public Integer getAttrIntellect() {
		return attrIntellect;
	}
	
	public Integer getAttrVitality() {
		return attrVitality;
	}
	
	public Integer getMaxHitpoints() {
		return maxHitpoints;
	}
	
	public Integer getCurHitpoints() {
		return curHitpoints;
	}
	
	public Integer getMaxHunger() {
		return maxHunger;
	}
	
	public Integer getCurHunger() {
		return curHunger;
	}
	
	public Integer getCurExhaustion() {
		return curExhaustion;
	}
	
	public Integer getCurSaturation() {
		return curSaturation;
	}
	
	public Integer getMaxMana() {
		return maxMana;
	}
	
	public Integer getCurMana() {
		return curMana;
	}
	
	public Integer getCurLevel() {
		return curLevel;
	}
	
	public Integer getCurExp() {
		return curExp;
	}
	
	public Integer getMoney() {
		return money;
	}
	
	public Integer getBankMoney() {
		return bankMoney;
	}
	
	public String getLocWorld() {
		return locWorld;
	}
	
	public Integer getLocX() {
		return locX;
	}
	
	public Integer getLocY() {
		return locY;
	}
	
	public Integer getLocZ() {
		return locZ;
	}
	
	public Integer getLocPitch() {
		return locPitch;
	}
	
	public Integer getLocYaw() {
		return locYaw;
	}
	
	public DateTime getLastLoginDate() {
		return lastLoginDate;
	}
	
	public DateTime getCreateDate() {
		return createDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDescriptionOOC() {
		return descriptionOOC;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public Character getPatron() {
		return patron;
	}
	
	public ForeignCollection<Character> getVassals() {
		return vassals;
	}
	
	public Location getLocation() {
		return location.clone();
	}
	
	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAttrStrength(Integer attr) {
		attrStrength = attr;
	}
	
	public void setAttrAgility(Integer attr) {
		attrAgility = attr;
	}
	
	public void setAttrStamina(Integer attr) {
		attrStamina = attr;
	}
	
	public void setAttrSprit(Integer attr) {
		attrSpirit = attr;
	}
	
	public void setAttrResistance(Integer attr) {
		attrResistance = attr;
	}
	
	public void setAttrIntellect(Integer attr) {
		attrIntellect = attr;
	}
	
	public void setAttrVitality(Integer attr) {
		attrVitality = attr;
	}
	
	public void setMaxHitpoints(Integer maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}
	
	public void setCurHitpoints(Integer curHitpoints) {
		this.curHitpoints = curHitpoints;
	}
	
	public void setMaxHunger(Integer maxHunger) {
		this.maxHunger = maxHunger;
	}
	
	public void setCurHunger(Integer curHunger) {
		this.curHunger = curHunger;
	}
	
	public void setCurExhaustion(Integer curExhaustion) {
		this.curExhaustion = curExhaustion;
	}
	
	public void setCurSaturation(Integer curSaturation) {
		this.curSaturation = curSaturation;
	}
	
	public void setCurLevel(Integer curLevel) {
		this.curLevel = curLevel;
	}
	
	public void setCurExp(Integer curExp) {
		this.curExp = curExp;
	}
	
	public void setMoney(Integer money) {
		this.money = money;
	}
	
	public void setBankMoney(Integer bankMoney) {
		this.bankMoney = bankMoney;
	}
	
	public void setLocWorld(String world) {
		locWorld = world;
		try{
			if(TerraCraft.server.getWorld(world) == null) return;
			location.setWorld(TerraCraft.server.getWorld(world));
		} catch (Exception e){
			location.setWorld(TerraCraft.server.getWorlds().get(0));
		}
	}
	
	public void setLocX(Integer x) {
		locX = x;
		if (x == null)
			return;
		location.setX(x);
	}
	
	public void setLocY(Integer y) {
		locY = y;
		if (y == null)
			return;
		location.setY(y);
	}
	
	public void setLocZ(Integer z) {
		locZ = z;
		if (z == null)
			return;
		location.setZ(z);
	}
	
	public void setLocPitch(Integer pitch) {
		locPitch = pitch;
		if (pitch == null)
			return;
		location.setPitch(pitch);
	}
	
	public void setLocYaw(Integer yaw) {
		locYaw = yaw;
		if (yaw == null)
			return;
		location.setYaw(yaw);
	}
	
	public void setLastLoginDate(DateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDescriptionOOC(String descriptionOOC) {
		this.descriptionOOC = descriptionOOC;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setPatron(Character patron) {
		this.patron = patron;
	}
	
	public void setLocation(Location loc) {
		location = loc;
		locWorld = loc.getWorld().getName();
		locX = loc.getBlockX();
		locY = loc.getBlockY() + 1;
		locZ = loc.getBlockZ();
		locPitch = Math.round(loc.getPitch());
		locYaw = Math.round(loc.getYaw());
	}
	
	// Init
	
	
	public ContainerData getContainerData() {
		return containerData;
	}

	
	public void setContainerData(ContainerData containerData) {
		this.containerData = containerData;
	}

	
	public Container getContainer() {
		// TODO: Move This Code to CharacterFactory / CharacterManager
		if (containerData == null){
			container = ContainerFactory.create(CharacterContainer.class, 39);
			((CharacterContainer) container).setCharacter(this);
			container.update();
			containerData = container.getDao();
		}
		
		if (container == null){
			containerData.refresh();
			container = ContainerFactory.create(containerData);
			((CharacterContainer) container).setCharacter(this);
			container.update();
		}
		
		if (container.getDao() != containerData){
			container.setDao(containerData);
			TerraLogger.error("Container Dao diffrenet from Character Container Dao... Character[getContainer()]");
			container.refresh();
		}
		return container;
	}

	
	public void setContainer(Container container) {
		this.container = container;
	}

	public Character() {}
	
	// Override Methods
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		return id.equals(((Character) other).getId());
	}
	
	@Override
	public String toString() {
		return "Character[ID: " + getId() + ", Name: " + getName() + ", Owner: " + getAccount().toString() + "]";
	}
	
	public String getColouredName() {
		return getName();
	}
}
