package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.listeners;

import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.enums.TCDebug;
import uk.co.terragaming.code.terracraft.events.inventory.BlockContainerCloseEvent;
import uk.co.terragaming.code.terracraft.events.inventory.BlockContainerOpenEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ContainerData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ContainerFactory;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;
import uk.co.terragaming.code.terracraft.utils.Scheduler;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.blocks.BlockUtils;
import uk.co.terragaming.code.terracraft.utils.blocks.ChestUtils;

import com.google.common.collect.Lists;

public class BlockContainerEvents implements Listener{
	
	@EventHandler
	public void onBlockContainerOpenEvent(BlockContainerOpenEvent e){
		BlockContainer container = e.getContainer();
		
		if (container instanceof ChestContainer){
			if (!PlayerEffects.hasEffect((Player) e.getPlayer(), PlayerEffect.INVISIBLE))
				ChestUtils.openChest(container.getBlock());
		}
	}
	
	@EventHandler
	public void onBlockContainerCloseEvent(BlockContainerCloseEvent e){
		BlockContainer container = e.getContainer();
		
		if (container instanceof ChestContainer){
			if (!PlayerEffects.hasEffect((Player) e.getPlayer(), PlayerEffect.INVISIBLE))
				ChestUtils.closeChest(container.getBlock());
		}
	}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event){
		BlockState[] states = event.getChunk().getTileEntities();
		
		Scheduler.runAsync(() -> {
			List<BlockContainer> toUpdate = Lists.newArrayList();
			
			for (BlockState bs : states){
				if (!(bs instanceof InventoryHolder)) continue;
				InventoryHolder holder = (InventoryHolder) bs;
				Inventory inv = holder.getInventory();
				
				Location loc = BlockUtils.getLocation(holder);
				if (loc == null) continue;
				
				World world = WorldRegistry.get(loc.getWorld());
				if (world == null) continue;
				
				BlockContainer container = world.getBlockContainer(loc);
				if (container == null){
					Class<? extends BlockContainer> type = BlockUtils.getContainerType(bs.getBlock());
					if (type == null) continue;
					createBlockContainer(type, inv.getSize(), world, loc);
				} else toUpdate.add(container);			
			}
			
			try{
				ContainerData.dao.callBatchTasks((Callable<Void>) () -> {
					toUpdate.forEach((BlockContainer c) -> updateBlockContainer(c));
					return null;					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkUnload(ChunkUnloadEvent event){
		if (event.isCancelled()) return;
		final BlockState[] blockStates = event.getChunk().getTileEntities();
		Scheduler.runAsync(() -> {
			try {
				ContainerData.dao.callBatchTasks((Callable<Void>) () -> {
					for (BlockState bs : blockStates){
						if (!(bs instanceof InventoryHolder)) continue;
						InventoryHolder holder = (InventoryHolder) bs;
						
						Location loc = BlockUtils.getLocation(holder);
						if (loc == null) continue;
						
						World world = WorldRegistry.get(loc.getWorld());
						if (world == null) continue;
						
						BlockContainer container = world.getBlockContainer(loc);
						if (container == null) continue;
						container.update();
						container.getItems().keySet().forEach(i -> ItemSystem.get().remove(i));
						TerraLogger.debug(TCDebug.CHESTS, "Uploaded %s", container);
					}
					return null;
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}); 
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.isCancelled()) return;
		
		Block block = event.getBlock();
		Location loc = block.getLocation();
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null) return;
		
		Class<? extends BlockContainer> type = BlockUtils.getContainerType(block);
		if (type == null) return;
		
		Scheduler.runAsync(() -> {
			createBlockContainer(type, 27, world, loc);
		});
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event){
		if (event.isCancelled()) return;

		Block block = event.getBlock();
		Location loc = block.getLocation();
		World world = WorldRegistry.get(loc.getWorld());
		if (world == null) return;
		
		BlockContainer container = world.getBlockContainer(loc);
		if (container == null) return;
		
		Scheduler.runAsync(() -> {
			container.destory();
		}); 
	}
	
	private void createBlockContainer(Class<? extends BlockContainer> type, Integer size, World world, Location loc){
		Long ts = System.currentTimeMillis();
		BlockContainer container = ContainerFactory.create(type, size);
		container.setWorld(world);
		container.setLocation(loc);
		container.update();
		container.refresh();
		ItemSystem.get().addContainer(container);
		TerraLogger.debug(TCDebug.CHESTS, "Created %s in %s in <h>%sms<r>", container, world, System.currentTimeMillis() - ts);
	}
	
	private void updateBlockContainer(BlockContainer container){
		Long ts = System.currentTimeMillis();
		container.update();
		TerraLogger.debug(TCDebug.CHESTS, "Loaded %s in <h>%sms<r>", container, System.currentTimeMillis() - ts);
	}
	
}
