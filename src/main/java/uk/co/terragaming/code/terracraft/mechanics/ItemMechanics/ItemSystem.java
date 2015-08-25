package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.WorldContainer;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;


public class ItemSystem implements Iterable<Item>{	
	
	private static ItemSystem instance;
	
	public static ItemSystem get(){
		return instance;
	}
	
	public ItemSystem(){
		instance = this;
	}
	
	public static Collection<Item> items(){
		return ItemMechanics.getInstance().getItemSystem().items.values();
	}
	
	public static Collection<Container> containers(){
		return ItemMechanics.getInstance().getItemSystem().containers;
	}
	
	private HashMap<Integer, Item> items = new HashMap<>(); // itemId, item
	
	public Item get(Integer id){
		if (!has(id)) return null;
		return items.get(id);
	}
	
	public boolean has(Integer id){
		return items.containsKey(id);
	}
	
	public boolean has(Item i){
		return has(i.getId());
	}
	
	public void put(Integer id, Item i){
		items.put(id, i);
	}
	
	public void put(Item i){
		put(i.getId(), i);
	}

	public void delete(Item i) {
		items.remove(i.getId());
	}
	
	private HashMap<World, List<ChestContainer>> chestItems = new HashMap<>();
	private HashMap<World, WorldContainer> droppedItems = new HashMap<>();
	private HashSet<Container> containers = new HashSet<>();
	
	public HashSet<Container> getContainers(){
		return containers;
	}
	
	public void addContainer(Container container){
		containers.add(container);
		
		if (container instanceof WorldContainer){
			WorldContainer c = ((WorldContainer) container);
			droppedItems.put(c.getWorld(), c);
		}
		
		if (container instanceof ChestContainer){
			ChestContainer c = ((ChestContainer) container);
			chestItems.putIfAbsent(c.getWorld(), Lists.newArrayList());
			chestItems.get(c.getWorld()).add(c);
		}
	}
	
	public void removeContainer(Container container){
		containers.remove(container);
		
		if (container instanceof WorldContainer){
			WorldContainer c = ((WorldContainer) container);
			droppedItems.remove(c);
		}
		
		if (container instanceof ChestContainer){
			ChestContainer c = ((ChestContainer) container);
			chestItems.get(c.getWorld()).remove(c);
		}
	}

	public Collection<WorldContainer> getDroppedItems() {
		return droppedItems.values();
	}
	
	public WorldContainer getDroppedItems(World world){
		return droppedItems.get(world);
	}
	
	public Collection<ChestContainer> getChests(World world) {
		return chestItems.get(world);
	}
	
	@Override
	public Iterator<Item> iterator() {
		List<Iterator<? extends Item>> iterators = Lists.newArrayList();
		for (Container c : containers) iterators.add(c.iterator());
		return Iterators.concat(iterators.iterator());
	}
	
}
