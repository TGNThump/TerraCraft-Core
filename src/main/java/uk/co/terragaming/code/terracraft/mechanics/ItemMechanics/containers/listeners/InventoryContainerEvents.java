package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.listeners;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.InventoryContainer;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;


public class InventoryContainerEvents implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryOpenEvent(InventoryOpenEvent e){
		if (e.isCancelled()) return;
		if (e.getInventory().getHolder() == null) return;
		if (e.getInventory().getHolder() instanceof Player) return;
		if (e.getInventory().getHolder() instanceof InventoryContainer){ onInventoryContainerOpen(e); return; }
		e.setCancelled(true);
		if (PlayerEffects.hasEffect((Player) e.getPlayer(), PlayerEffect.NOINTERACT)) return;
		if (e.getInventory().getHolder() instanceof Entity) return;		
		
		Location loc = getLocation(e.getInventory().getHolder());
		if (loc == null) return;
		
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null) return;
		
		BlockContainer container = world.getBlockContainer(loc);
		if (container == null) return;
		
		e.getPlayer().openInventory(container.getInventory());
	}

	private void onInventoryContainerOpen(InventoryOpenEvent e) {
		InventoryContainer container = (InventoryContainer) e.getInventory().getHolder();
		if (container instanceof ChestContainer && !PlayerEffects.hasEffect((Player)e.getPlayer(), PlayerEffect.INVISIBLE))
			playChestOpen((ChestContainer) container);
	    
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryCloseEvent(InventoryCloseEvent e){
		if (e.getInventory().getHolder() == null) return;
		if (e.getInventory().getHolder() instanceof InventoryContainer){ onInventoryContainerClose(e); return; }
	}
	
	private void onInventoryContainerClose(InventoryCloseEvent e) {
		InventoryContainer container = (InventoryContainer) e.getInventory().getHolder();
		if (container instanceof ChestContainer && !PlayerEffects.hasEffect((Player)e.getPlayer(), PlayerEffect.INVISIBLE))
			playChestClose((ChestContainer) container);
	}

	private void playChestOpen(ChestContainer chest) {

	}
	
	private void playChestClose(ChestContainer container) {

	}

	private Location getLocation(InventoryHolder holder) {
		if (holder instanceof BlockState) return ((BlockState) holder).getLocation();
		if (holder instanceof DoubleChest) return ((DoubleChest) holder).getLocation();
		//if (holder instanceof Entity) return ((Entity) holder).getLocation();
		return null;
	}
}
