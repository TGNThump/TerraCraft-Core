package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.events.item.ItemDestroyEvent;
import uk.co.terragaming.code.terracraft.events.item.ItemMoveEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.MaterialPersister;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ContainerData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ComponentFactory;
import uk.co.terragaming.code.terracraft.utils.Assert;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("unchecked")
@DatabaseTable(tableName = "tcItems")
public class Item implements Iterable<ItemComponent>, Comparable<Item>{

	public static Dao<Item, Integer> dao = DatabaseMechanics.getInstance().getDao(Item.class);
	
	@DatabaseField(generatedId = true, canBeNull = false)
	private Integer id;
	
	@DatabaseField(canBeNull = false, persisterClass = MaterialPersister.class)
	private Material icon;
	
	@DatabaseField(canBeNull = true)
	private Integer slotId;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "containerId")
	private ContainerData containerData;
	
	
	public ContainerData getContainerData() {
		return containerData;
	}

	
	public void setContainerData(ContainerData containerData) {
		this.containerData = containerData;
	}

	@DatabaseField(canBeNull = false)
	private String name = "";
	
	@DatabaseField(canBeNull = false, columnName = "type")
	private String itemType = "";
	
	@DatabaseField(canBeNull = false, columnName = "class")
	private String itemClass = "";
	
	private Container container;
	
	@ForeignCollectionField(eager = true, columnName = "itemId", foreignFieldName = "item")
	public ForeignCollection<ItemComponentData> componentData;
	
	private HashMap<Class<? extends ItemComponent>, ItemComponent> components = new HashMap<>();
	
	public void update(){
		if (container == null || containerData == null){
			destroy();
			TerraLogger.debug("Deleted Item[<p>%s<r>] named '<p>%s<r>' - cleanup - container null", getId(), getName());
			return;
		}
		if (containerData == null){
			containerData = container.getDao();
		}
		
		for (ItemComponent component : this){
			update(component);
		}
		
		try {
			Item.dao.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refresh(){
		
		components.clear();
		
		try {
			Item.dao.refresh(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!ItemSystem.get().has(this))
			ItemSystem.get().put(this);
		
		for (ItemComponentData d : componentData){
			add(d);
		}
	}

	public <T extends ItemComponent> T get(Class<T> type) throws NullPointerException{
		if (type == null) throw new NullPointerException();
		if (!has(type)) return null;
		return (T) components.get(type);
	}

	public <T extends ItemComponent> T add(Class<T> type){
		if (type == null) throw new NullPointerException();
		if (has(type)){ remove(type); }
		ItemComponent component = ComponentFactory.create(type, this);
		components.put(type, component);
		Bukkit.getPluginManager().registerEvents(component, TerraCraft.plugin);
		return (T) component;
	}
	
	public <T extends ItemComponent> T add(T toClone){
		return add(ComponentFactory.clone(toClone, this));
	}
	
	public <T extends ItemComponent> T add(ItemComponentData data){
		Assert.notNull(data);
		ItemComponent component = ComponentFactory.create(data);
		components.put(component.getClass(), component);
		refresh(component);
		Bukkit.getPluginManager().registerEvents(component, TerraCraft.plugin);
		return (T) component;
	}
	
	public <T extends ItemComponent> T as(Class<T> type){
		if (has(type)) return get(type);
		else return add(type);
	}
	
	public boolean has(Class<? extends ItemComponent> type){
		return components.containsKey(type);
	}
	
	public boolean remove(Class<? extends ItemComponent> type){
		if (!has(type)) return false;
		ItemComponent component = components.get(type);
		componentData.remove(component.dao);
		components.remove(type);
		HandlerList.unregisterAll(component);
		return true;
	}
	
	public void update(Class<? extends ItemComponent> type){
		update(get(type));
	}
	
	public void update(ItemComponent component){
		component.update();
	}
	
	public void refresh(Class<? extends ItemComponent> type){
		refresh(get(type));
	}
	
	public void refresh(ItemComponent component){
		component.refresh();
	}
	
	// Getters and Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSlotId() {
		return slotId;
	}
	
	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
	
	// Overrides

	public Material getIcon() {
		return icon;
	}

	public void setIcon(Material icon) {
		this.icon = icon;
	}

	
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getItemType() {
		return itemType;
	}

	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	
	public String getItemClass() {
		return itemClass;
	}

	
	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	@Override
	public int compareTo(Item o) {
		return getId().compareTo(o.getId());
	}

	@Override
	public Iterator<ItemComponent> iterator() {
		return components.values().iterator();
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName() + "[<h>" + getId() + "<r>]";
	}

	public boolean moveTo(Container to) {
		ItemMoveEvent event = new ItemMoveEvent(this, container, to);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) return false;
		TerraLogger.info("%s moved from %s to %s", this, container, to);
		
		Integer oldSlot = getSlotId();
		Container oldContainer = container;
		container.remove(this);
		Boolean success = to.add(this);
		if (success){
			update();
			return true;
		} else {
			oldContainer.add(this, oldSlot);
			return false;
		}
	}
	
	public void destroy(){
		ItemDestroyEvent event = new ItemDestroyEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		
		try {
			Item.dao.delete(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ItemMechanics.getInstance().getItemSystem().delete(this);
	}
	
}
