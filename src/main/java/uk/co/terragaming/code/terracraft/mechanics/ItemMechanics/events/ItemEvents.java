package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.events;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RenderComponent;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;
import uk.co.terragaming.code.terracraft.utils.ItemUtils;

public class ItemEvents implements Listener{

	public ItemEvents(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				for (World w : WorldRegistry.worlds())
					for (final Iterator<org.bukkit.entity.Item> iter = w.getItemEntities().iterator(); iter.hasNext();){
						org.bukkit.entity.Item item = iter.next();
						if (item.isValid()) continue;
						iter.remove();
						ItemStack is = item.getItemStack();
						Item i = ItemUtils.getItem(is);
						if (i == null) continue;
						i.destroy();
						
					}
			}
			
		}, 20, 20);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent event){
		if (event.isCancelled()) return;
		ItemStack is = event.getItemDrop().getItemStack();
		Item i = ItemUtils.getItem(is);
		if (i == null){
			event.setCancelled(true);
			return;
		}

		World w = WorldRegistry.get(event.getItemDrop().getWorld());
		
		boolean success = i.moveTo(w.getContainer());
		if (success){
			event.getItemDrop().setItemStack(i.as(RenderComponent.class).render());
			w.getItemEntities().add(event.getItemDrop());
			return;
		}
		else {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event){
		if (event.isCancelled()) return;
		ItemStack is = event.getItem().getItemStack();
		Item i = ItemUtils.getItem(is);
		if (i == null){
			event.setCancelled(true);
			return;
		}
		
		Player player = event.getPlayer();
		Account account = AccountRegistry.getAccount(player);
		if (account == null){
			event.setCancelled(true);
			return;
		}
		Character character = account.getActiveCharacter();
		if (character == null){
			event.setCancelled(true);
			return;
		}
		
		World w = WorldRegistry.get(event.getItem().getWorld());
		
		boolean success = i.moveTo(character.getContainer());
		if (success){		
			event.getItem().setItemStack(i.as(RenderComponent.class).render());
			w.getItemEntities().remove(event.getItem());
			return;
		} else {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemEntityDespawnEvent(ItemDespawnEvent event){
		if (event.isCancelled()) return;
		ItemStack is = event.getEntity().getItemStack();
		Item i = ItemUtils.getItem(is);
		if (i == null) return;
		
		World w = WorldRegistry.get(event.getEntity().getWorld());
		w.getItemEntities().remove(event.getEntity());
		i.destroy();
	}
	
	@EventHandler
	public void onChunkLoadEvent(ChunkLoadEvent event){
		World w = WorldRegistry.get(event.getWorld());
		for (Entity e : event.getChunk().getEntities()){
			if (!(e instanceof org.bukkit.entity.Item)) continue;
			w.getItemEntities().add((org.bukkit.entity.Item) e);
		}
	}
	
	@EventHandler
	public void onChunkUnloadEvent(ChunkUnloadEvent event){
		World w = WorldRegistry.get(event.getWorld());
		for (Entity e : event.getChunk().getEntities()){
			if (!(e instanceof org.bukkit.entity.Item)) continue;
			w.getItemEntities().remove(e);
		}
	}
}
