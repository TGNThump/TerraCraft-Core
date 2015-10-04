package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.events.inventory.BlockContainerCloseEvent;
import uk.co.terragaming.code.terracraft.events.inventory.BlockContainerOpenEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.InventoryContainer;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;
import uk.co.terragaming.code.terracraft.utils.blocks.BlockUtils;
import uk.co.terragaming.code.terracraft.utils.item.ItemUtils;

public class InventoryContainerEvents implements Listener{
	
	// Inventory Interact
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClickEvent(InventoryClickEvent  e){
		if (e.isCancelled()) return;
		e.setCancelled(true);
		
		Container inv = getContainer(e.getInventory());
		Container clicked = getContainer(e.getClickedInventory());
		Container charInv = getContainer(e.getWhoClicked().getInventory());
		
		if (inv == null) return;
		if (clicked == null) return;
		if (charInv == null) return;
		
		Item current = ItemUtils.getItem(e.getCurrentItem());
		Item cursor = ItemUtils.getItem(e.getCursor());
		
		if (current == null  && cursor == null) return;
		
//		TerraLogger.debug(TCDebug.ITEMS, "");
//		TerraLogger.debug(TCDebug.ITEMS, "INV: %s", inv);
//		TerraLogger.debug(TCDebug.ITEMS, "CLICKED: %s", clicked);
//		TerraLogger.debug(TCDebug.ITEMS, "CURRENT: %s", current);
//		TerraLogger.debug(TCDebug.ITEMS, "CURSOR: %s", cursor);
//		TerraLogger.debug(TCDebug.ITEMS, "ACTION: %s", e.getAction());
//		TerraLogger.debug(TCDebug.ITEMS, "");
		
		e.setCancelled(false);
		
		switch (e.getAction()){
			case NOTHING:
			case PICKUP_ALL:
				break;
			case PLACE_ALL:
				if (cursor == null) return;
				if (cursor.getContainer() == clicked){
					e.setCancelled(!cursor.moveToSlot(e.getSlot()));
					return;
				}
						
				e.setCancelled(!cursor.moveTo(clicked, e.getSlot()));
				
				break;
			case MOVE_TO_OTHER_INVENTORY:
				if (current == null) return;
				if (clicked == charInv){
					e.setCancelled(!current.moveTo(inv));
				} else {
					e.setCancelled(!current.moveTo(charInv));
				}
				break;
			default:
				e.setCancelled(true);
				break;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDragEvent(InventoryDragEvent e){
		e.setCancelled(true);
	}
	
	private Container getContainer(Inventory inv){
		if (inv.getHolder() instanceof Player){
			Player player = (Player) inv.getHolder();
			Account account = AccountRegistry.getAccount(player);
			return account.getActiveCharacter().getContainer();
		}
		
		if (inv.getHolder() instanceof InventoryContainer){
			return (Container) inv.getHolder();
		}
		
		return null;
	}
	
	// Inventory Open and Close

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryOpenEvent(InventoryOpenEvent e){
		if (e.isCancelled()) return;
		if (e instanceof BlockContainerOpenEvent) return;
		if (e.getInventory().getHolder() == null) return;
		if (e.getInventory().getHolder() instanceof Player) return;
		if (e.getInventory().getHolder() instanceof InventoryContainer){ onInventoryContainerOpen(e); return; }
		
		e.setCancelled(true);
		
		if (PlayerEffects.hasEffect((Player) e.getPlayer(), PlayerEffect.NOINTERACT)) return;
		if (e.getInventory().getHolder() instanceof Entity){ onEntityInventoryOpen(e); return;}
		else onBlockInventoryOpen(e);
	}
	
	// Called when a {@link BlockState} or {@link DoubleChest} inventory is opened.
	private void onBlockInventoryOpen(InventoryOpenEvent e) {
		Location loc = BlockUtils.getLocation(e.getInventory().getHolder());
		if (loc == null) return;
		
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null) return;
		
		BlockContainer container = world.getBlockContainer(loc);
		if (container == null) return;
		
		e.getPlayer().openInventory(container.getInventory());
	}

	// Called when a {@link Entity} inventory is opened.
	private void onEntityInventoryOpen(InventoryOpenEvent e) {
		// TODO Entity Inventories.
	}
	
	// Called when a {@link InventoryContianer} is opened.
	private void onInventoryContainerOpen(InventoryOpenEvent e) {
		InventoryContainer container = (InventoryContainer) e.getInventory().getHolder();
		
		if (container instanceof BlockContainer){
			BlockContainerOpenEvent e1 = new BlockContainerOpenEvent(e.getView());
			e1.setContainer((BlockContainer) container);
			Bukkit.getPluginManager().callEvent(e1);
			e.setCancelled(e1.isCancelled());
		}
	    
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryCloseEvent(InventoryCloseEvent e){
		if (e.getInventory().getHolder() == null) return;
		if (e instanceof BlockContainerCloseEvent) return;
		if (e.getInventory().getHolder() instanceof InventoryContainer){ onInventoryContainerClose(e); return; }
	}
	
	private void onInventoryContainerClose(InventoryCloseEvent e) {
		InventoryContainer container = (InventoryContainer) e.getInventory().getHolder();
		
		if (container instanceof BlockContainer){
			BlockContainerCloseEvent e1 = new BlockContainerCloseEvent(e.getView());
			e1.setContainer((BlockContainer) container);
			Bukkit.getPluginManager().callEvent(e1);
		}
	}
}
