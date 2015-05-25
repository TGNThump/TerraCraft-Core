package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import org.bukkit.Location;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;

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
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "raceId")
	private CharacterRace race;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private Integer attrStrength;
	
	@DatabaseField(canBeNull = false)
	private Integer attrAgility;
	
	@DatabaseField(canBeNull = false)
	private Integer attrStamina;
	
	@DatabaseField(canBeNull = false)
	private Integer attrSpirit;
	
	@DatabaseField(canBeNull = false)
	private Integer attrResistance;
	
	@DatabaseField(canBeNull = false)
	private Integer attrIntellect;
	
	@DatabaseField(canBeNull = false)
	private Integer attrVitality;
	
	@DatabaseField(canBeNull = false)
	private Integer maxHitpoints;
	
	@DatabaseField(canBeNull = false)
	private Integer curHitpoints;
	
	@DatabaseField(canBeNull = false)
	private Integer maxHunger;
	
	@DatabaseField(canBeNull = false)
	private Integer curHunger;
	
	@DatabaseField(canBeNull = false)
	private Integer maxMana;
	
	@DatabaseField(canBeNull = false)
	private Integer curMana;
	
	@DatabaseField(canBeNull = false)
	private Integer curLevel;
	
	@DatabaseField(canBeNull = false)
	private Integer curExp;
	
	@DatabaseField(canBeNull = false)
	private Integer money;
	
	@DatabaseField(canBeNull = false)
	private Integer bankMoney;
	
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
	
//	@ForeignCollectionField(eager = true)
//	private ForeignCollection<ItemInstance> items;
	
	@ForeignCollectionField(eager = true, foreignFieldName = "patron")
	private ForeignCollection<Character> vassals;
	
	@DatabaseField(canBeNull = true, columnName = "patronId", foreign = true)
	private Character patron;
	
	// Non Database Fields
	
	private Location location = TerraCraft.server.getWorlds().get(0).getSpawnLocation();
	
	// Getters
	
	public Integer getId(){ return id; }
	public Account getAccount(){ return account; }
	public CharacterRace getRace(){ return race; }
	public String getName(){ return name; }
	
	public Integer getAttrStrength(){ return attrStrength; }
	public Integer getAttrAgility(){ return attrAgility; }
	public Integer getAttrStamina(){ return attrStamina; }
	public Integer getAttrSprit(){ return attrSpirit; }
	public Integer getAttrResistance(){ return attrResistance; }
	public Integer getAttrIntellect(){ return attrIntellect; }
	public Integer getAttrVitality(){ return attrVitality; }
	
	public Integer getMaxHitpoints(){ return maxHitpoints; }
	public Integer getCurHitpoints(){ return curHitpoints; }
	
	public Integer getMaxHunger(){ return maxHunger; }
	public Integer getCurHunger(){ return curHunger; }
	
	public Integer getMaxMana(){ return maxMana; }
	public Integer getCurMana(){ return curMana; }
	
	public Integer getCurLevel(){ return curLevel; }
	public Integer getCurExp(){ return curExp; }
	
	public Integer getMoney(){ return money; }
	public Integer getBankMoney(){ return bankMoney; }
	
	public String getLocWorld(){ return locWorld; }
	public Integer getLocX(){ return locX; }
	public Integer getLocY(){ return locY; }
	public Integer getLocZ(){ return locZ; }
	public Integer getLocPitch(){ return locPitch; }
	public Integer getLocYaw(){ return locYaw; }
	
	public DateTime getLastLoginDate(){ return lastLoginDate; }
	public DateTime getCreateDate(){ return createDate; }
	
	public String getDescription(){ return description; }
	public String getDescriptionOOC(){ return descriptionOOC; }
	public String getNotes(){ return notes; }
	
	public Character getPatron(){ return patron; }
	public ForeignCollection<Character> getVassals(){ return vassals; }
	
//	public ForeignCollection<ItemInstance> getItems(){ return items; } 
	
	public Location getLocation(){ return location.clone(); }
	
	// Setters
	
	public void setId(Integer id){ this.id = id; }
	public void setAccount(Account account){ this.account = account; }
	public void setRace(CharacterRace race){ this.race = race; }
	public void setName(String name){ this.name = name; }
	
	public void setAttrStrength(Integer attr){ this.attrStrength = attr; }
	public void setAttrAgility(Integer attr){ this.attrAgility = attr; }
	public void setAttrStamina(Integer attr){ this.attrStamina = attr; }
	public void setAttrSprit(Integer attr){ this.attrSpirit = attr; }
	public void setAttrResistance(Integer attr){ this.attrResistance = attr; }
	public void setAttrIntellect(Integer attr){ this.attrIntellect = attr; }
	public void setAttrVitality(Integer attr){ this.attrVitality = attr; }

	public void setMaxHitpoints(Integer maxHitpoints){ this.maxHitpoints = maxHitpoints; }
	public void setCurHitpoints(Integer curHitpoints){ this.curHitpoints = curHitpoints; }
	
	public void setMaxHunger(Integer maxHunger){ this.maxHunger = maxHunger; }
	public void setCurHunger(Integer curHunger){ this.curHunger = curHunger; }
	
	public void setCurLevel(Integer curLevel){ this.curLevel = curLevel; }
	public void setCurExp(Integer curExp){ this.curExp = curExp; }
	
	public void setMoney(Integer money){ this.money = money; }
	public void setBankMoney(Integer bankMoney){ this.bankMoney = bankMoney; }
	
	public void setLocWorld(String world){
		this.locWorld = world;
		this.location.setWorld(TerraCraft.server.getWorld(world));
	}
	
	public void setLocX(Integer x){
		this.locX = x;
		if (x == null) return;
		this.location.setX(x);
	}
	
	public void setLocY(Integer y){
		this.locY = y;
		if (y == null) return;
		this.location.setY(y);
	}
	
	public void setLocZ(Integer z){
		this.locZ = z;
		if (z == null) return;
		this.location.setZ(z);
	}
	
	public void setLocPitch(Integer pitch){
		this.locPitch = pitch;
		if (pitch == null) return;
		this.location.setPitch(pitch);
	}
	
	public void setLocYaw(Integer yaw){
		this.locYaw = yaw;
		if (yaw == null) return;
		this.location.setYaw(yaw);
	}
	
	public void setLastLoginDate(DateTime lastLoginDate){ this.lastLoginDate = lastLoginDate; }
	public void setCreateDate(DateTime createDate){ this.createDate = createDate; }
	
	public void setDescription(String description){ this.description = description; }
	public void setDescriptionOOC(String descriptionOOC){ this.descriptionOOC = descriptionOOC; }
	public void setNotes(String notes){ this.notes = notes; }
	
	public void setPatron(Character patron){ this.patron = patron; }
	
	public void setLocation(Location loc){
		this.location = loc;
		this.locWorld = loc.getWorld().getName();
		this.locX = loc.getBlockX();
		this.locY = loc.getBlockY() + 1;
		this.locZ = loc.getBlockZ();
		this.locPitch = Math.round(loc.getPitch());
		this.locYaw = Math.round(loc.getYaw());
	}
	
	// Init
	
	public Character(){}
	
	// Override Methods
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null || other.getClass() != getClass()) return false;
		return id.equals(((Character) other).getId());
	}
	
	@Override
	public String toString(){
		return "Character[ID: " + getId() + ", Name: " + getName() + ", Owner: " + getAccount().toString() + "]";
	}
}
