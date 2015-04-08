package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.comphenix.attribute.AttributeStorage;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics.CustomItem;
import uk.co.terragaming.code.terracraft.enums.CharacterAttribute;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemClass;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.utils.StringTools;

public class ItemInstance {
	private int id;
	private int itemId;
	private int ownerId;
	private Integer slotid;
	private String name;
	private Material material;
	private ItemQuality quality;
	private ItemBinding binding;
	private int minDamageMod;
	private int maxDamageMod;
	private String data;
	
	private HashMap<CharacterAttribute, Integer> moddedAttributes = new HashMap<CharacterAttribute, Integer>();		// [AttributeName, Value Modifier]
	
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
	public Integer getSlotid() {
		return slotid;
	}
	public void setSlotid(Integer slotid) {
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
	public ItemQuality getQuality() {
		if (quality == null){
			return this.getItem().getQuality();
		}
		return quality;
	}
	public void setQuality(ItemQuality quality) {
		this.quality = quality;
	}
	public ItemBinding getBinding() {
		if(binding == null){
			return ItemBinding.NONE;
		}
		return binding;
	}
	public void setBinding(ItemBinding binding) {
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
	public HashMap<CharacterAttribute, Integer> getRawModdedAttributes() {
		return moddedAttributes;
	}
	
	public Integer getRawModdedAttribute(CharacterAttribute attribute){
		if (!this.moddedAttributes.containsKey(attribute)){ return 0;}
		return this.moddedAttributes.get(attribute);
	}
	
	public Integer setRawModdedAttribute(CharacterAttribute attribute, Integer value){
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
	
	public Integer getModdedAttribute(CharacterAttribute attribute){
		return (this.getItem().getAttributeModifier(attribute) + this.getRawModdedAttribute(attribute));
	}
	
	public String getColouredName(){
		return ItemQuality.getChatColor(getQuality()) + "" + ChatColor.BOLD + getName() + ChatColor.RESET;
	}
	
	public ItemStack getItemStack(){
		CustomItem item = new CustomItem(getMaterial());
		
		item.setName(getColouredName());

		item.addLore(ChatColor.GRAY + (getItem().getItemClass().equals(ItemClass.MELEE) || getItem().getItemClass().equals(ItemClass.RANGE) ? StringTools.toNormalCase(getItem().getItemClass().toString()) + " Weapon" :  StringTools.toNormalCase(getItem().getItemClass().toString())) + "                              " + ChatColor.GOLD + this.getItem().getType().toString());
		item.addLore(ChatColor.DARK_GRAY + "" + getMinDamage() + " - " + getMaxDamage() + " Damage");
		
		if (getBinding().equals(ItemBinding.ACCOUNT)){
			item.addLore(ChatColor.GRAY + "Heirloom");
		} else if (getBinding().equals(ItemBinding.CHARACTER)){
			item.addLore(ChatColor.GRAY + "Soulbound");
		}
		
		AttributeStorage storage = AttributeStorage.newTarget(item.getItemStack(), TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft"));
		
		storage.setData("TCID: " + this.getId());
		
		return storage.getTarget();
	}
}
