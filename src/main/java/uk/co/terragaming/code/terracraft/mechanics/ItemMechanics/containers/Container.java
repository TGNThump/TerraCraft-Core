package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.co.terragaming.code.terracraft.enums.TCDebug;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public abstract class Container implements Comparable<Container>, Iterable<Item>{
	
	protected Integer id;
	protected Integer size;
	
	protected ContainerData dao;
	
	protected JSONObject data;
	
	protected HashMap<Integer, Item> items = new HashMap<>(); // slotId, item
	
	public boolean add(Item item) {
		if (item == null) return false;
		for (int i = 0; i < size; i++){
			if (items.containsKey(i)) continue;
			addOverride(item, i);
			return true;
		}
		return false;
	}
	
	public void addOverride(Item item, Integer slot){
		if (items.containsKey(slot)) items.get(slot).destroy();
		items.put(slot, item);
		item.setSlotId(slot);
		item.setContainer(this);
		item.setContainerData(dao);
	}
	
	public boolean add(Item item, Integer slot){
		if (item == null) return false;
		if (items.containsKey(slot)) return false;
		addOverride(item, slot);
		return true;
	}
	
	public void remove(Item item){
		if (item.getSlotId() == null) return;
		items.remove(item.getSlotId());
		item.setContainer(null);
		item.setContainerData(null);
	}
	
	// Update and Refresh
	
	public void update(){
		for(Integer id : items.keySet()){
			Item i = items.get(id);
			i.setContainerData(dao);
			i.setSlotId(id);
			i.update();
		}
		dao.setData(data.toJSONString());
		dao.setSize(size);
		dao.update();
	}
	
	public void refresh(){
		dao.refresh();
		id = dao.getId();
		size = dao.getSize();

		setData(dao.getData());
		
		items.clear();
		for (Item i : dao.getItems()){
			i.refresh();
			i.setContainer(this);
			i.setContainerData(this.dao);
			items.put(i.getSlotId(), i);
		}
	}
	
	public void destory(){
		for (Item i : items.values()){
			i.destroy();
		}
		ItemSystem.get().removeContainer(this);
		dao.destory();
		TerraLogger.debug(TCDebug.CHESTS, "Destroyed %s", this);
	}
		
	// Getters and Setters
	
	public void setData(String data){
		if (data == null){
			this.data =  new JSONObject();
			return;
		}
		if (data.isEmpty()){
			this.data =  new JSONObject();
			return;
		}
		JSONParser parser = new JSONParser();
		try {
			this.data = (JSONObject)parser.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getContainerId() {
		return id;
	}
	
	public void setContainerId(Integer id) {
		this.id = id;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	
	public ContainerData getDao() {
		return dao;
	}

	
	public void setDao(ContainerData dao) {
		this.dao = dao;
	}

	
	public JSONObject getData() {
		return data;
	}

	
	public void setData(JSONObject data) {
		this.data = data;
	}

	
	public HashMap<Integer, Item> getItems() {
		return items;
	}

	
	public void setItems(HashMap<Integer, Item> items) {
		this.items = items;
	}

	public Iterator<Item> iterator() {
		return items.values().iterator();
	}
	
	@Override
	public int compareTo(Container o) {
		return id.compareTo(o.getContainerId());
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName() + "[<h>" + id + "<r>]";
	}
	
	public int getId(){
		return getDao().getId();
	}
}
