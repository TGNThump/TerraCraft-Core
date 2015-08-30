package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.event.Listener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.PropertyFactory;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;

@SuppressWarnings("unchecked")
public abstract class ItemComponent implements Iterable<Property<?>>, Listener{

	public ItemComponentData dao;
	
	private Item item;
	
	private JSONObject data = new JSONObject();
	private HashSet<Property<?>> properties = new HashSet<>();
	
	public String render(Integer lvl){
		return "";
	}
	
	private Object get(String key){
		return data.get(key);
	}
	
	private void set(String key, Object value){
		data.put(key, value);
	}
	
	public <T> Property<T> get(String key, Class<T> type){
		Property<T> property = (Property<T>) PropertyFactory.create(key, type, get(key));
		properties.add(property);
		return property;
	}

	public void update() {
		dao.setData(getData().toJSONString());
		dao.setType(this.getClass().getSimpleName());
		dao.update();
	}

	public void refresh() {
		dao.refresh();
		setData(dao.getData());
		
	}
	
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
			setData((JSONObject)parser.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// Getters and Setters
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public JSONObject getData() {
		for (Property<?> property : this){
			set(property.getKey(), property.encode());
		}
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
		for (Property<?> property : this){
			property.decode(get(property.getKey()));
		}
	}
	
	public void setDao(ItemComponentData dao){
		this.dao = dao;
	}
	
	@Override
	public Iterator<Property<?>> iterator() {
		return properties.iterator();
	}
	
}
