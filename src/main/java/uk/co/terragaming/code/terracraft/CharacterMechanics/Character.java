package uk.co.terragaming.code.terracraft.CharacterMechanics;

import java.util.HashMap;

import org.bukkit.Location;
import org.joda.time.DateTime;

public class Character {
	private int id;
	private int accountId;
	private int raceId;
	
	private String name;
	private HashMap<String, Integer> attributes = new HashMap<String, Integer>();
	
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
	public void setAttributes(HashMap<String, Integer> attributes) {
		this.attributes = attributes;
	}
	public int getAttribute(String attribute){
		return this.attributes.get(attribute);
	}
	public void setAttribute(String attribute, Integer value){
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
