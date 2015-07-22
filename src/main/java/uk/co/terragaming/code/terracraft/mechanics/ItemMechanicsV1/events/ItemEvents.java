package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.events;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.ItemManager;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.ItemMechanicsV1;

import com.google.common.collect.Lists;

public class ItemEvents implements Listener {
	
	private ItemMechanicsV1 itemMechanics;
	public static ArrayList<Item> itemEntities;
	private ItemInstanceRegistry registry;
	
	public ItemEvents() {
		itemMechanics = ItemMechanicsV1.getInstance();
		itemEntities = Lists.newArrayList();
		registry = itemMechanics.getItemInstanceRegistry();
		Bukkit.getScheduler().runTaskTimer(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				checkItemEntities();
			}
		}, 1, 1);
	}
	
	// Inventory Events
	
	@EventHandler
	public void onPrepareItemCraft(PrepareItemCraftEvent event) {
		for (ItemStack i : event.getInventory()) {
			if (!ItemManager.isItemInstance(i)) {
				continue;
			}
			event.getInventory().setResult(new ItemStack(Material.AIR));
			return;
		}
	}
	
	@EventHandler
	public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
		if (event.isCancelled())
			return;
		if (!ItemManager.isItemInstance(event.getItem()))
			return;
		event.setCancelled(true);
	}
	
	// Player Events
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		Item i = event.getItemDrop();
		if (!ItemManager.isItemInstance(i.getItemStack()))
			return;
		Integer id = ItemManager.getItemInstanceId(i.getItemStack());
		ItemInstance item = registry.getItem(id);
		
		Player player = event.getPlayer();
		if (!AccountRegistry.hasAccount(player))
			return;
		Account account = AccountRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		
		item.setSlotId(null);
		item.setCharacter(null);
		
		item = ItemManager.updateItemInstance(item, i.getItemStack());
		
		if (character != null) {
			registry.removeFromCharacter(character, id);
		}
		
		registry.addItemToDroped(item);
		try {
			ItemMechanicsV1.getInstance().getItemInstanceDao().update(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (event.isCancelled())
			return;
		Item i = event.getItem();
		if (itemEntities.contains(i)) {
			itemEntities.remove(event.getItem());
		}
		if (!ItemManager.isItemInstance(i.getItemStack()))
			return;
		Integer id = ItemManager.getItemInstanceId(i.getItemStack());
		ItemInstance item = registry.getItem(id);
		
		Player player = event.getPlayer();
		if (!AccountRegistry.hasAccount(player))
			return;
		Account account = AccountRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		if (character == null)
			return;
		
		item.setCharacter(character);
		item.setSlotId(null);
		item = ItemManager.updateItemInstance(item, i.getItemStack());
		
		registry.removeItemFromDropped(item);
		registry.addItemToCharacter(character, item);
		try {
			ItemMechanicsV1.getInstance().getItemInstanceDao().update(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		if (event.isCancelled())
			return;
		if (!ItemManager.isItemInstance(event.getItem()))
			return;
		event.setDamage(0);
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled())
			return;
		if (!ItemManager.isItemInstance(event.getItem()))
			return;
		event.setCancelled(true);
	}
	
	// Entity Events
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		if (event.isCancelled())
			return;
		Item i = event.getEntity();
		if (!ItemManager.isItemInstance(i.getItemStack()))
			return;
		
		itemEntities.add(i);
	}
	
	public void onItemDeath(Item i) {
		if (!ItemManager.isItemInstance(i.getItemStack()))
			return;
		Integer id = ItemManager.getItemInstanceId(i.getItemStack());
		
		ItemInstance item = registry.getItem(id);
		registry.removeItemFromDropped(item);
		registry.removeItem(item.getId());
		
		try {
			ItemMechanicsV1.getInstance().getItemInstanceDao().delete(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void checkItemEntities() {
		ItemInstanceRegistry registry = itemMechanics.getItemInstanceRegistry();
		for (Iterator<Item> iter = itemEntities.iterator(); iter.hasNext();) {
			Item item = iter.next();
			ItemStack is = item.getItemStack();
			Integer id = ItemManager.getItemInstanceId(is);
			if (!registry.hasDroppedItem(id)) {
				iter.remove();
				continue;
			}
			if (!item.isValid()) {
				iter.remove();
				onItemDeath(item);
			}
		}
	}
	
	// Item Events
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemDespawn(ItemDespawnEvent event) {
		if (event.isCancelled())
			return;
		Item i = event.getEntity();
		if (!ItemManager.isItemInstance(i.getItemStack()))
			return;
		Integer id = ItemManager.getItemInstanceId(i.getItemStack());
		ItemInstance item = registry.getItem(id);
		
		registry.removeItemFromDropped(item);
		registry.removeItem(item.getId());
		
		try {
			ItemMechanicsV1.getInstance().getItemInstanceDao().delete(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Chunk Events
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		for (Entity entity : event.getChunk().getEntities()) {
			if (!(entity instanceof Item)) {
				continue;
			}
			Item item = (Item) entity;
			
			if (!itemEntities.contains(item)) {
				itemEntities.add(item);
			}
		}
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		for (Entity entity : event.getChunk().getEntities()) {
			if (!(entity instanceof Item)) {
				continue;
			}
			Item item = (Item) entity;
			
			if (itemEntities.contains(item)) {
				itemEntities.remove(item);
			}
		}
	}
}
