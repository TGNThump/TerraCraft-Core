package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemQuality;
import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemHistory;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.utils.CustomItem;

import com.comphenix.attribute.AttributeStorage;
import com.google.common.collect.Lists;


public abstract class Item {
	
	private Integer id;
	private String name;
	private Material icon;
	private Integer amount;
	
	private ItemQuality rarity;
	private ItemBindType bindType;
	private ItemBinding binding;
	
	private String typeString;
	
	private HashMap<Class<? extends ItemAttribute>,ItemAttribute> attributes;
	private List<ItemHistory> history;
	private ItemSource source;
	
	private Integer currentDurability;
	private Integer maxDurability;
	
	public Item(ItemSource itemSource){
		name = "";
		icon = Material.STONE;
		rarity = ItemQuality.COMMON;
		bindType = ItemBindType.NONE;
		setBinding(ItemBinding.NONE);
		attributes = new HashMap<>();
		history = Lists.newArrayList();
		source = itemSource;
		currentDurability = 0;
		maxDurability = -1;
		amount = 1;
	}
	
	public abstract String getClassString();
	
	public ItemStack render(){
		CustomItem item = new CustomItem(getIcon());
		
		item.setName(getColouredName());
		item.setStackSize(amount);
		item.addLore("<silver>" + getClassString() + "                              <gold>" + getTypeString());
		
		item.addLore(renderAttributes());
		
		if (!getBinding().equals(ItemBinding.NONE)){
			item.addLore("<silver>" + (getBinding().equals(ItemBinding.ACCOUNT) ? "Heirloom" : "Soulbound"));
		}
		
		if (hasDurability()){
			if (isBroken()){
				item.addLore("<grey><italic>Broken<r>");
				item.setDurability((short) getIcon().getMaxDurability());
			} else {
				double maxDamage = getIcon().getMaxDurability();
				double percent = (double) getCurrentDurability() / getMaxDurability();
				
				if (percent > 1) percent = 1;
				if (percent < 0) percent = 0;
				
				double durability = (Math.round(1 - percent) * maxDamage);
				item.setDurability((short) durability);				
			}
		}
		
		AttributeStorage storage = AttributeStorage.newTarget(item.getItemStack(), ItemMechanics.getInstance().getAttributeUUID());
		//storage.setData("TCID: " + getId());
		
		return storage.getTarget();
	}
	
	public List<String> renderAttributes(){
		List<String> attrText = Lists.newArrayList();
		for (ItemAttribute attribute : attributes.values()){
			String attr = attribute.render();
			if (attr == null) continue;
			if (attr == "") continue;
			attrText.add(attr);
		}
		return attrText;
	}
	
	public String getColouredName(){
		if (isBroken()){
			char[] chars = getName().toCharArray();
			boolean italic = true;
			String name = "";
			
			String color = ItemQuality.getChatColor(getRarity()).toString();
			
			for (char c : chars) {
				name += color;
				if (italic == true) {
					name += "<italic>";
				}
				name += c + "<reset>";
				italic = !italic;
			}
			
			return name;
		}
		
		return ItemQuality.getChatColor(getRarity()) + "<bold>" + getName() + "<r>";
	}
	
	public boolean hasDurability(){
		return (getMaxDurability() != -1);
	}
	
	public boolean isBroken(){
		return (hasDurability() && getCurrentDurability() == 0);
	}
	
	public void addAttribute(ItemAttribute attribute){
		attributes.put(attribute.getClass(), attribute);
	}
	
	public ItemAttribute getAttribute(Class<? extends ItemAttribute> attrClass){
		return attributes.get(attrClass);
	}

	public void removeAttribute(ItemAttribute attr){
		removeAttribute(attr.getClass());
	}
	
	public void removeAttribute(Class<? extends ItemAttribute> attrClass){
		attributes.remove(attrClass);
	}
	
	public boolean hasAttribute(Class<? extends ItemAttribute> attrClass){
		return attributes.containsKey(attrClass);
	}
	
	// Getters and Setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Material getIcon() {
		return icon;
	}

	public void setIcon(Material icon) {
		this.icon = icon;
	}
	
	public ItemQuality getRarity() {
		return rarity;
	}
	
	public void setRarity(ItemQuality rarity) {
		this.rarity = rarity;
	}
	
	public ItemBindType getBindType() {
		return bindType;
	}
	
	public void setBindType(ItemBindType bindType) {
		this.bindType = bindType;
	}
	
	public ItemBinding getBinding() {
		return binding;
	}

	public void setBinding(ItemBinding binding) {
		this.binding = binding;
	}

	public Collection<ItemAttribute> getAttributes() {
		return attributes.values();
	}
	
	public List<ItemHistory> getHistory() {
		return history;
	}
	
	public void setHistory(List<ItemHistory> history) {
		this.history = history;
	}
	
	public ItemSource getSource() {
		return source;
	}
	
	public void setSource(ItemSource source) {
		this.source = source;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	
	public Integer getCurrentDurability() {
		return currentDurability;
	}

	
	public void setCurrentDurability(Integer currentDurability) {
		this.currentDurability = currentDurability;
	}

	
	public Integer getMaxDurability() {
		return maxDurability;
	}

	
	public void setMaxDurability(Integer maxDurability) {
		this.maxDurability = maxDurability;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
}
