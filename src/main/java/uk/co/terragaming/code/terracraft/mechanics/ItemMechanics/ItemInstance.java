package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.ItemRarity;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.DurabilityComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RarityComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ComponentFactory;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemBaseComponentLink;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemComponentLink;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemInstanceComponentLink;
import uk.co.terragaming.code.terracraft.utils.CustomItem;

import com.comphenix.attribute.AttributeStorage;
import com.google.common.collect.Lists;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemInstance")
public class ItemInstance {
	
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "instanceId")
	private Integer id;
	
	@DatabaseField(foreign = true, canBeNull = false, columnName = "baseId")
	private ItemBase base;
	
	@DatabaseField(canBeNull = false)
	private Integer amount = 1;

	@DatabaseField(foreign = true, canBeNull = false, columnName = "ownerId")
	private Character owner;
	
	@DatabaseField(canBeNull = false)
	private Integer slotId;
	
	@ForeignCollectionField(eager = true, columnName = "instanceId", foreignFieldName = "itemInstance")
	private ForeignCollection<ItemInstanceComponentLink> componentLinks;
	
	// Components
	
	private HashMap<Class<? extends ItemComponent>, ItemComponent> components = new HashMap<>();

	public void addComponent(ItemComponent component) {
		components.put(component.getClass(), component);
	}
	
	private List<String> renderComponents() {		
		List<String> ret = Lists.newArrayList();
		for (ItemComponent component : components.values()){
			String value = component.render();
			if (value == null) continue;
			if (value == "") continue;
			ret.add(value);
		}
		return ret;
	}
	
	// Serialisation
	
	public void upload() throws SQLException{
		ItemMechanics.getInstance().getItemInstanceDao().update(this);
		
		for (ItemComponent component : components.values()){
			component.upload();
		}
	}
	
	public void download() throws SQLException{
		ItemMechanics.getInstance().getItemInstanceDao().refresh(this);
		ItemMechanics.getInstance().getItemBaseDao().refresh(getBase());
		getBase().getComponentLinks().refreshCollection();
		
		HashMap<Integer, ArrayList<ItemComponentLink>> links = new HashMap<>();
		
		for (ItemBaseComponentLink link : getBase().getComponentLinks()){
			if (links.containsKey(link.getComponentId())) return;
			links.put(link.getComponentId(), Lists.newArrayList());
			links.get(link.getComponentId()).add(link);
		}
		
		for (ItemInstanceComponentLink link : componentLinks){
			if (!links.containsKey(link.getComponentId())) return;
			links.get(link.getComponentId()).add(link);
		}
		
		for (ArrayList<ItemComponentLink> linkSet : links.values()){
			ItemBaseComponentLink baseLink = null;
			ItemInstanceComponentLink instanceLink = null;
			
			for (ItemComponentLink link : linkSet){
				if (link instanceof ItemBaseComponentLink){
					if (baseLink != null) return;
					baseLink = (ItemBaseComponentLink) link;
					continue;
				}
				if (link instanceof ItemInstanceComponentLink){
					if (instanceLink != null) return;
					instanceLink = (ItemInstanceComponentLink) link;
					continue;
				}
			}
			
			if (baseLink == null || instanceLink == null) continue;
			ItemComponent component = ComponentFactory.create(baseLink, instanceLink);
			component.setItem(this);
			addComponent(component);
		}
		
	}
	
	// Render
	
	public ItemStack render(){
		CustomItem item = new CustomItem(getIcon());
		item.setName(getColouredName());
		item.setStackSize(amount);
		item.addLore("<silver>" + getClassName() + "                              <gold>" + getTypeName());
		item.addLore(renderComponents());
		item.setDurability(getDurability());
		
		AttributeStorage storage = AttributeStorage.newTarget(item.getItemStack(), ItemMechanics.getInstance().getAttributeUUID());
		storage.setData("TCID: " + getId());
		
		return storage.getTarget();
	}
	
	private short getDurability() {
		if (components.containsKey(DurabilityComponent.class)){
			DurabilityComponent component = (DurabilityComponent) components.get(DurabilityComponent.class);
			return component.getItemDurability();
		}
		return 0;
	}
	
	public String getColouredName(){
		String colour = "<silver>";
		String weight = "<bold>";
		
		if (components.containsKey(RarityComponent.class)){
			RarityComponent component = (RarityComponent) components.get(RarityComponent.class);
			colour = "" + ItemRarity.getChatColor(component.getRarity());
		}
		
		if (components.containsKey(DurabilityComponent.class)){
			DurabilityComponent component = (DurabilityComponent) components.get(DurabilityComponent.class);
			if (component.isBroken()) weight = "<italic>";
		}
		
		return colour + weight + getName() + "<r>";
	}
	
	// Getters and Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ItemBase getBase() {
		return base;
	}
	
	public void setBase(ItemBase base) {
		this.base = base;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	
	public ForeignCollection<ItemInstanceComponentLink> getComponentLinks() {
		return componentLinks;
	}
	
	public String getName(){
		return getBase().getName();
	}
	
	public Material getIcon(){
		return getBase().getIcon();
	}
	
	public String getClassName(){
		return getBase().getClassName();
	}
	
	public String getTypeName(){
		return getBase().getTypeName();
	}
}
