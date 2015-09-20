package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.listeners;

import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.TCDebug;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ContainerData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ContainerFactory;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;
import uk.co.terragaming.code.terracraft.utils.BlockUtils;
import uk.co.terragaming.code.terracraft.utils.Scheduler;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;


public class ChestEvents implements Listener{
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event){
		BlockState[] states = event.getChunk().getTileEntities();
		
		Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				List<ChestContainer> toUpdate = Lists.newArrayList();
				
				for (BlockState bs : states){
					if (!(bs instanceof Chest) && !(bs instanceof DoubleChest)) continue;
					Chest c = (Chest) bs;
					Inventory inv = c.getBlockInventory();
					
					World world = WorldRegistry.get(c.getWorld());
					if (world == null) continue;
					
					ChestContainer chest = world.getChest(c.getLocation());
					if (chest == null){
						// Create new chest...
						Long ts = System.currentTimeMillis();
						chest = ContainerFactory.create(ChestContainer.class, inv.getSize());
						chest.setWorld(world);
						chest.setLocation(c.getLocation());
						chest.update();
						chest.refresh();
						ItemSystem.get().addContainer(chest);
						TerraLogger.debug(TCDebug.CHESTS, "Created %s in %s in <h>%sms<r>", chest, world, System.currentTimeMillis() - ts);
						continue;
					}
					toUpdate.add(chest);
				}
				
				try {
					ContainerData.dao.callBatchTasks(new Callable<Void>(){

						@Override
						public Void call() throws Exception {
							for (ChestContainer chest : toUpdate){
								Long ts = System.currentTimeMillis();
								chest.update();
								TerraLogger.debug(TCDebug.CHESTS, "Loaded %s in <h>%sms<r>", chest, System.currentTimeMillis() - ts);
							}
							return null;
						}
						
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkUnload(ChunkUnloadEvent event){
		if (event.isCancelled()) return;
		final BlockState[] blockStates = event.getChunk().getTileEntities();
		
		Scheduler.runAsync(new Runnable(){

			@Override
			public void run() {
				try {
					ContainerData.dao.callBatchTasks(new Callable<Void>(){

						@Override
						public Void call() throws Exception {
							for (BlockState bs : blockStates){
								if (!(bs instanceof Chest) && !(bs instanceof DoubleChest)) continue;
								Chest c = (Chest) bs;
								
								World world = WorldRegistry.get(c.getWorld());
								if (world == null) continue;
								
								ChestContainer chest = world.getChest(c.getLocation());
								if (chest == null) continue;
								chest.update();
								chest.getItems().keySet().forEach(i -> ItemSystem.get().remove(i));
								TerraLogger.debug(TCDebug.CHESTS, "Uploaded %s", chest);
							}
							return null;
						}
						
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.isCancelled()) return;
		if (!event.getBlock().getType().equals(Material.CHEST)) return;
		
		Long tstamp = System.currentTimeMillis();
		
		Chest c = (Chest) event.getBlock().getState();
		Inventory inv = c.getBlockInventory();
		Location loc = event.getBlock().getLocation();
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null) return;
		
		ChestContainer chest = ContainerFactory.create(ChestContainer.class, inv.getSize());
		chest.setWorld(world);
		chest.setLocation(loc);
		chest.update();
		chest.refresh();
		ItemSystem.get().addContainer(chest);
		TerraLogger.debug(TCDebug.CHESTS, "Created %s in %s <h>%sms<r>", chest, world, System.currentTimeMillis() - tstamp);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event){
		if (event.isCancelled()) return;
		if (!event.getBlock().getType().equals(Material.CHEST)) return;
		
		Location loc = event.getBlock().getLocation();
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null) return;
		
		ChestContainer chest = world.getChest(loc);
		if (chest == null) return;
		chest.destory();
		
		TerraLogger.debug(TCDebug.CHESTS, "Destroyed %s and removed from %s", chest, chest.getWorld());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryOpenEvent(InventoryOpenEvent e){
		if (e.isCancelled()) return;
		if (!(e.getInventory().getHolder() instanceof Chest) && !(e.getInventory().getHolder() instanceof DoubleChest)) return;

		Location loc = null;
		
		if (e.getInventory().getHolder() instanceof Chest){
			Chest c = (Chest) e.getInventory().getHolder();
			loc = c.getLocation();
		} else {
			DoubleChest c = (DoubleChest) e.getInventory().getHolder();
			loc = c.getLocation();
		}
		
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null){	e.setCancelled(true); return; }
		
		ChestContainer chest = world.getChest(loc);
		if (chest == null){	e.setCancelled(true); return; }
		
		e.setCancelled(true);
		e.getPlayer().openInventory(chest.getInventory());
		BlockUtils.playChestAction((Chest) e.getInventory().getHolder(), true);
    }
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void OnInventoryCloseEvent(InventoryCloseEvent e){
		if (!(e.getInventory().getHolder() instanceof ChestContainer)) return;
		ChestContainer c = (ChestContainer) e.getInventory().getHolder();
		
		Block block = c.getWorld().getBukkitWorld().getBlockAt(c.getLocation());
		if (!(block.getState() instanceof Chest)) return;
		
		Chest chest = (Chest) block.getState();
		BlockUtils.playChestAction(chest, false);
	}
	
}
