package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;

import com.google.common.collect.Lists;

public class ItemInstanceRegistry {
	
	private static HashMap<Integer, ItemInstance> itemsById = new HashMap<>();
	private static HashMap<Character, ArrayList<Integer>> itemsByCharacter = new HashMap<>();
	private static ArrayList<Integer> dropedItems = Lists.newArrayList();
	
	public void addItemIfAbsent(ItemInstance item) {
		itemsById.putIfAbsent(item.getId(), item);
	}
	
	public void addItemToCharacter(Character character, ItemInstance item) {
		addItemIfAbsent(item);
		
		if (character == null)
			return;
		itemsByCharacter.putIfAbsent(character, Lists.newArrayList());
		itemsByCharacter.get(character).add(item.getId());
	}
	
	public void addItemToDroped(ItemInstance item) {
		addItemIfAbsent(item);
		dropedItems.add(item.getId());
	}
	
	public boolean hasItem(Integer id) {
		return itemsById.containsKey(id);
	}
	
	public ItemInstance getItem(Integer id) {
		return itemsById.get(id);
	}
	
	public void removeItem(Integer id) {
		itemsById.remove(id);
	}
	
	public ArrayList<Integer> getItemIds(Character character) {
		if (!itemsByCharacter.containsKey(character))
			return Lists.newArrayList();
		return itemsByCharacter.get(character);
	}
	
	public ArrayList<ItemInstance> getItems(Character character) {
		ArrayList<ItemInstance> ret = Lists.newArrayList();
		
		if (!itemsByCharacter.containsKey(character))
			return ret;
		
		for (Integer id : itemsByCharacter.get(character)) {
			ret.add(getItem(id));
		}
		
		return ret;
	}
	
	public List<ItemInstance> getDroppedItems() {
		List<ItemInstance> ret = Lists.newArrayList();
		
		for (Integer id : dropedItems) {
			ret.add(getItem(id));
		}
		
		return ret;
	}
	
	public void removeFromCharacter(Character character, Integer id) {
		if (!itemsByCharacter.containsKey(character))
			return;
		itemsByCharacter.get(character).remove(id);
	}
	
	public void removeItemFromDropped(ItemInstance item) {
		if (!dropedItems.contains(item.getId()))
			return;
		dropedItems.remove(item.getId());
	}
	
	public void clearItems(Character character) {
		if (!itemsByCharacter.containsKey(character))
			return;
		itemsByCharacter.remove(character);
	}
	
	public boolean hasDroppedItem(Integer id) {
		return dropedItems.contains(id);
	}
}
