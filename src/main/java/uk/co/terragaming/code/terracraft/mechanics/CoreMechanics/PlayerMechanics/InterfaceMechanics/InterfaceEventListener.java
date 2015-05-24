package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;

public class InterfaceEventListener implements Listener{

	private HashMap<UUID, PlayerInterfaceInstance> instances = new HashMap<>();
	
	// Methods
	
	public void registerInstance(UUID uuid, PlayerInterfaceInstance instance){
		this.instances.put(uuid, instance);
	}
	
	public void removeInstance(UUID uuid, PlayerInterfaceInstance instance){
		this.instances.remove(uuid, instance);
	}
	
	// Events
	
	@EventHandler
	public void onPluginDisable(PluginDisableEvent event){
		for (PlayerInterfaceInstance instance : instances.values()){
			instance.close();
			instance = null;
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player p = (Player) event.getWhoClicked();
		if (!instances.containsKey(p.getUniqueId())) return;
		PlayerInterfaceInstance instance = instances.get(p.getUniqueId());
		if (!instance.getInv().getTitle().equals(p.getOpenInventory().getTitle())) return;
		if (!instance.isOpen()) return;
		event.setCancelled(true);
		instance.click(event.getSlot());
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		Player p = (Player) event.getPlayer();
		if (!instances.containsKey(p.getUniqueId())) return;
		PlayerInterfaceInstance instance = instances.get(p.getUniqueId());
		if (!instance.getIFace().closable){
			Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable()
			{
			    @Override
			    public void run()
			    {
			    	instances.get(p.getUniqueId()).open();
			    }
			});
		} else {
			instance.deRegister();
			instance = null;
		}
	}
}
