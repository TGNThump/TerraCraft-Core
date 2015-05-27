package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.events;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;

import com.google.common.collect.Lists;

public class ItemEvents implements Listener{

	private ItemMechanics itemMechanics;
	private ArrayList<Item> itemEntities;
	
	public ItemEvents(){
		itemMechanics = ItemMechanics.getInstance();
		itemEntities = Lists.newArrayList();
		Bukkit.getScheduler().runTaskTimer(TerraCraft.plugin, new Runnable(){
			public void run() { checkItemEntities(); }
		}, 1, 1);
	}
	
	// Inventory Events
	
	
	// Player Events
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event){
		ItemStack is = event.getItemDrop().getItemStack();
		AccountRegistry accRegistry = AccountMechanics.getInstance().getRegistry();
		Player player = event.getPlayer();
		if (!accRegistry.hasAccount(player)) return;
		Account account = accRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		if (character == null) return;
		if (is == null) return;
		Integer id = ItemManager.getItemInstanceId(is);
		if (id == null) return;
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		if (!registry.hasItem(id)) return;
		ItemInstance item = registry.getItem(id);
		
		item.setSlotId(null);
		item.setCharacter(null);
		item = ItemManager.updateItemInstance(item, is);
		
		registry.removeFromCharacter(character, id);
		registry.addItemToDroped(item);
		try {
			ItemMechanics.getInstance().getItemInstanceDao().update(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPickupItem(PlayerPickupItemEvent event){
		if (itemEntities.contains(event.getItem())) itemEntities.remove(event.getItem());
		
		ItemStack is = event.getItem().getItemStack();
		AccountRegistry accRegistry = AccountMechanics.getInstance().getRegistry();
		Player player = event.getPlayer();
		if (!accRegistry.hasAccount(player)) return;
		Account account = accRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		if (character == null) return;
		if (is == null) return;
		Integer id = ItemManager.getItemInstanceId(is);
		if (id == null) return;
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		if (!registry.hasItem(id)) return;
		ItemInstance item = registry.getItem(id);
		
		item.setCharacter(character);
		item.setSlotId(null);
		item = ItemManager.updateItemInstance(item, is);
		
		registry.removeItemFromDropped(item);
		registry.addItemToCharacter(character, item);
		try {
			ItemMechanics.getInstance().getItemInstanceDao().update(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Entity Events
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event){
		Item i = event.getEntity();
		ItemStack is = i.getItemStack();
		if (is == null) return;
		Integer id = ItemManager.getItemInstanceId(is);
		if (id == null) return;
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		if (!registry.hasItem(id)) return;
		
		itemEntities.add(i);
	}
	
	public void onItemDeath(Item i){
		ItemStack is = i.getItemStack();
		if (is == null) return;
		Integer id = ItemManager.getItemInstanceId(is);
		if (id == null) return;
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		if (!registry.hasItem(id)) return;
		
		ItemInstance item = registry.getItem(id);
		registry.removeItemFromDropped(item);
		registry.removeItem(item.getId());
		
		try {
			ItemMechanics.getInstance().getItemInstanceDao().delete(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void checkItemEntities(){
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		for (Iterator<Item> iter = itemEntities.iterator(); iter.hasNext();){
			Item item = iter.next();
			ItemStack is = item.getItemStack();
			Integer id = ItemManager.getItemInstanceId(is);
			if (!registry.hasDroppedItem(id)) iter.remove();
			if (!item.isValid()){
				iter.remove();
				onItemDeath(item);
			}
		}
	}
	
	// Item Events
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemDespawn(ItemDespawnEvent event){
		ItemStack is = event.getEntity().getItemStack();
		if (is == null) return;
		Integer id = ItemManager.getItemInstanceId(is);
		if (id == null) return;
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		if (!registry.hasItem(id)) return;
		ItemInstance item = registry.getItem(id);
		
		registry.removeItemFromDropped(item);
		registry.removeItem(item.getId());
		
		try {
			ItemMechanics.getInstance().getItemInstanceDao().delete(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
