package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.ItemRarity;
import uk.co.terragaming.code.terracraft.enums.ItemSource;


public class SerializedItem {
	
	private Integer id;
	private String name;
	private Material icon;
	
	private Character owner;
	private Integer slotId;
	
	private ItemRarity rarity;
	private ItemBindType bindType;
	private ItemBinding binding;
	
	private Integer curDurability;
	private Integer maxDurability;
	
	
	private String typePath;
	private ItemSource itemSource;
	
	private String attributeJSON;
	
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
	
	public Character getOwner() {
		return owner;
	}
	
	public void setOwner(Character owner) {
		this.owner = owner;
	}
	
	public Integer getSlotId() {
		return slotId;
	}
	
	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}
	
	public ItemRarity getRarity() {
		return rarity;
	}
	
	public void setRarity(ItemRarity rarity) {
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
	
	public Integer getCurDurability() {
		return curDurability;
	}
	
	public void setCurDurability(Integer curDurability) {
		this.curDurability = curDurability;
	}
	
	public Integer getMaxDurability() {
		return maxDurability;
	}
	
	public void setMaxDurability(Integer maxDurability) {
		this.maxDurability = maxDurability;
	}
	
	public String getTypePath() {
		return typePath;
	}
	
	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}
	
	public ItemSource getItemSource() {
		return itemSource;
	}
	
	public void setItemSource(ItemSource itemSource) {
		this.itemSource = itemSource;
	}

	public String getAttributeJSON() {
		return attributeJSON;
	}

	public void setAttributeJSON(String attributeJSON) {
		this.attributeJSON = attributeJSON;
	}
}
