package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.utils.Assert;

import com.google.common.collect.Lists;


public class ItemInstanceRegistry {
	
	private static ItemInstanceRegistry instance;
	
	private HashMap<Integer, ItemInstance> itemInstanceById;
	private HashMap<Integer, ArrayList<Integer>> instanceIdByCharId;
	private ArrayList<Integer> droppedItemsById;
	
	public void init(){
		instance = this;
		itemInstanceById = new HashMap<>();
		instanceIdByCharId = new HashMap<>();
		droppedItemsById = Lists.newArrayList();
	}
	
	// Item Instance Registry
	
	public static ItemInstance get(Integer id){
		return instance.itemInstanceById.get(id);
	}
	
	public static void putIfAbsent(ItemInstance item){
		putIfAbsent(item.getId(), item);
	}
	
	public static void putIfAbsent(Integer id, ItemInstance item){
		Assert.notNull(item);
		if (has(id)) return;
		put (id, item);
	}
	
	public static void put(ItemInstance item){
		put(item.getId(), item);
	}
	
	public static void put(Integer id, ItemInstance item){
		Assert.notNull(item);
		instance.itemInstanceById.put(id, item);
	}
	
	public static void remove(ItemInstance item){
		remove(item.getId());
	}
	
	public static void remove(Integer id){
		instance.itemInstanceById.remove(id);
	}
	
	public static boolean has(Integer id){
		return instance.itemInstanceById.containsKey(id);
	}
	
	public static boolean has(ItemInstance item){
		return has(item.getId());
	}

	// Character Item Registry
	
	public static void addItemToCharacter(Character character, ItemInstance item){
		Assert.notNull(character);
		Assert.notNull(item);
		putIfAbsent(item);
		item.setOwner(character);
		addItemToCharacter(character.getId(), item.getId());
	}
	
	public static void addItemToCharacter(Integer charId, Integer itemId){		
		instance.instanceIdByCharId.putIfAbsent(charId, Lists.newArrayList());
		instance.instanceIdByCharId.get(charId).add(itemId);
	}
	
	public static void removeItemFromCharacter(Character character, ItemInstance item){
		Assert.notNull(character);
		Assert.notNull(item);
		item.setOwner(null);
		removeItemFromCharacter(character.getId(), item.getId());
	}
	
	public static void removeItemFromCharacter(Integer charId, Integer itemId){
		if (!hasCharacter(charId)) return;
		instance.instanceIdByCharId.get(charId).remove((Object) itemId);
	}
	
	public static boolean hasCharacter(Character character){
		Assert.notNull(character);
		return hasCharacter(character.getId());
	}
	
	public static boolean hasCharacter(Integer charId){
		return instance.instanceIdByCharId.containsKey(charId);
	}
	
	public static ArrayList<ItemInstance> getCharactersItems(Character character){
		Assert.notNull(character);
		return getCharactersItems(character.getId());
	}
	
	public static ArrayList<ItemInstance> getCharactersItems(Integer charId){
		ArrayList<ItemInstance> ret = Lists.newArrayList();
		for (Integer id : instance.instanceIdByCharId.get(charId)){
			ret.add(get(id));
		}
		return ret;
	}
	
	public static ArrayList<Integer> getCharactersItemIds(Character character){
		Assert.notNull(character);
		return getCharactersItemIds(character.getId());
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> getCharactersItemIds(Integer charId){
		if (!hasCharacter(charId)) return Lists.newArrayList();
		return (ArrayList<Integer>) instance.instanceIdByCharId.get(charId).clone();
	}
	
	public static void clearCharactersItems(Character character){
		Assert.notNull(character);
		clearCharactersItems(character.getId());
	}

	public static void clearCharactersItems(Integer id) {
		if (!hasCharacter(id)) return;
		instance.instanceIdByCharId.remove(id);
	}
	
	// Dropped Item Registry
	
	public static void addItemToDropped(ItemInstance item){
		Assert.notNull(item);
		putIfAbsent(item);
		addItemToDropped(item.getId());
	}

	public static void addItemToDropped(Integer id) {
		if (!has(id)) return;
		instance.droppedItemsById.add(id);
	}
	
	public static void removeItemFromDropped(ItemInstance item){
		Assert.notNull(item);
		removeItemFromDropped(item.getId());
	}
	
	public static void removeItemFromDropped(Integer id){
		if (!hasDroppedItem(id)) return;
		instance.droppedItemsById.remove(id);
	}
	
	public static boolean hasDroppedItem(ItemInstance item){
		Assert.notNull(item);
		return hasDroppedItem(item.getId());
	}
	
	public static boolean hasDroppedItem(Integer id){
		return instance.droppedItemsById.contains(id);
	}
	
	public static ArrayList<ItemInstance> getDroppedItems(){
		ArrayList<ItemInstance> ret = Lists.newArrayList();
		for (Integer id : instance.droppedItemsById){
			ret.add(get(id));
		}
		return ret;
	}
}
