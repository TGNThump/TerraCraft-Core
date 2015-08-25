package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import java.util.HashSet;
import java.util.Iterator;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;

public class WorldContainer extends Container{
	
	private World world;
	private HashSet<Item> items = new HashSet<>();

	@SuppressWarnings("unchecked")
	@Override
	public void update(){
		data.put("world", world.getName());
		
		for(Item i : items){
			i.setContainerData(dao);
			i.setSlotId(null);
			i.update();
		}
		dao.setData(data.toJSONString());
		dao.setSize(null);
		dao.update();
	}
	
	@Override
	public void refresh(){
		dao.refresh();
		id = dao.getId();
		size = null;

		setData(dao.getData());
		
		world = WorldRegistry.get((String) data.get("world"));
		
		items.clear();
		for (Item i : dao.getItems()){
			i.refresh();
			i.setContainer(this);
			i.setContainerData(this.dao);
			items.add(i);
		}
	}
	
	public WorldContainer(){
		super.items = null;
		super.size = null;
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return items.iterator();
	}
	
	@Override
	public Integer getSize(){
		return null;
	}
	
	@Override
	public void setSize(Integer size) {}
	
	@Override
	public boolean addItem(Item item) {
		items.add(item);
		item.setSlotId(null);
		item.setContainer(this);
		item.setContainerData(dao);
		return true;
	}
	
}
