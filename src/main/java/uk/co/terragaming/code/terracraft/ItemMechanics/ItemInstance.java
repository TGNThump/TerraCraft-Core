package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemInstance {
	private int id;
	private int itemId;
	private int ownerId;
	private int slotid;
	private String name;
	private Material material;
	private String quality;
	private String binding;
	private int minDamageMod;
	private int maxDamageMod;
	private String data;
	
	private HashMap<String, Integer> moddedAttributes = new HashMap<String, Integer>();		// [AttributeName, Value Modifier]
	
	private Integer value;
	private int durability;
	
	
	public ItemInstance(int itemId){
		this.itemId = itemId;
	}
	
	public Item getItem(){
		return ItemMechanics.getInstance().getItemRegistry().getItem(itemId);
	}
	
	public boolean isBound(){
		return true;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getSlotid() {
		return slotid;
	}
	public void setSlotid(int slotid) {
		this.slotid = slotid;
	}
	public String getName() {
		if(name == null){
			return this.getItem().getName();
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Material getMaterial() {
		if(material == null){
			return this.getItem().getMaterial();
		}
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public String getQuality() {
		if (quality == null){
			return this.getItem().getQuality();
		}
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getBinding() {
		if(binding == null){
			return ItemBinding.NONE;
		}
		return binding;
	}
	public void setBinding(String binding) {
		this.binding = binding;
	}
	public int getMinDamageMod() {
		return minDamageMod;
	}
	
	public int getMinDamage(){
		return getMinDamageMod() + getItem().getMinBaseDamage();
	}
	
	public int getMaxDamage(){
		return getMaxDamageMod() + getItem().getMaxBaseDamage();
	}
	
	public void setMinDamageMod(int minDamageMod) {
		this.minDamageMod = minDamageMod;
	}
	public int getMaxDamageMod() {
		return maxDamageMod;
	}
	public void setMaxDamageMod(int maxDamageMod) {
		this.maxDamageMod = maxDamageMod;
	}
	public String getData() {
		if(data == null){
			return this.getItem().getData();
		}
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public HashMap<String, Integer> getRawModdedAttributes() {
		return moddedAttributes;
	}
	
	public Integer getRawModdedAttribute(String attribute){
		return this.moddedAttributes.get(attribute);
	}
	
	public Integer setRawModdedAttribute(String attribute, Integer value){
		return this.moddedAttributes.put(attribute, value);
	}
	
	public int getValue() {
		if(value == null){
			return this.getItem().getCost();
		}
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getDurability() {
		return durability;
	}
	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	public Integer getModdedAttribute(String attribute){
		return (this.getItem().getAttributeModifier(attribute) + this.getRawModdedAttribute(attribute));
	}
	
	public ItemStack getItemStack(){
		CustomItem item = new CustomItem(getMaterial());
		
		item.setName(ItemQuality.getChatColor(getQuality()) + "" + ChatColor.BOLD + getName());

		item.addLore(ChatColor.GRAY + (getItem().getItemClass() == ItemClass.MELEE || getItem().getItemClass() == ItemClass.RANGE ? getItem().getItemClass() + " Weapon" :  getItem().getItemClass()) + "                              " + ChatColor.GOLD + this.getItem().getType().toString());
		item.addLore(ChatColor.DARK_GRAY + "" + getMinDamage() + " - " + getMaxDamage() + " Damage");
		
		if (getBinding().equals(ItemBinding.ACCOUNT)){
			item.addLore(ChatColor.GRAY + "Heirloom");
		} else if (getBinding().equals(ItemBinding.CHARACTER)){
			item.addLore(ChatColor.GRAY + "Soulbound");
		}
		
		return item.getItemStack();
	}
}
