package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ItemEvents implements Listener {
	
	private ItemMechanics itemMechanics;
	private Random rand;
	
	public ItemEvents(){
		itemMechanics = ItemMechanics.getInstance();
		rand = new Random();
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event){
		// If the Damager is a Player...
		if (!(event.getDamager() instanceof Player)){ return; }
		Player damager = (Player) event.getDamager(); 
		
		// ... and the Item in hand is an TCItemInstance ...
		ItemStack is = damager.getItemInHand();
		ItemInstance item = itemMechanics.getItemInstance(is);
		if (item == null){ return; }
		
		// ... Get the name of the Damaged Entity ...
		Entity damaged = event.getEntity();
		String damagedName;
		if (damaged.getCustomName() == null){
			damagedName = "the " + ChatColor.BOLD + damaged.getType().toString().toLowerCase();
		} else if (damaged.getCustomName().isEmpty()){
			damagedName = "the " + ChatColor.BOLD + damaged.getType().toString().toLowerCase();
		} else {
			damagedName = ChatColor.BOLD + damaged.getCustomName();
		}
		
		// ... Set the correct damage value ...
		event.setDamage(randInt(item.getMinDamage(), item.getMaxDamage()));
		
		// ... and send a Debug Message.
		damager.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" + ChatColor.WHITE + "][" + ChatColor.DARK_AQUA + "DEBUG" + ChatColor.WHITE + "] Dealt " + ChatColor.DARK_GRAY + (int)event.getDamage() + ChatColor.RESET + " damage to " + damagedName + ChatColor.RESET + " using your " + item.getColouredName() + "." + ChatColor.RESET);
		
	}
	
	// Item Binding
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.PLAYER)){return;}
		
		List<ItemStack> dropList = event.getDrops();
		Iterator<ItemStack> iter = dropList.iterator();
		ItemStack is;
		ItemMechanics itemMechanics = ItemMechanics.getInstance();
		TerraLogger.debug("ManipulateDrops Event Called");
		while (iter.hasNext()) {
			is = iter.next();
			TerraLogger.debug("Item");
			ItemInstance itemInstance = itemMechanics.getItemInstance(is);
			if (itemInstance == null) {continue;}
			TerraLogger.debug("ItemInstance (ID: " + itemInstance.getId() + ")");
			switch (itemInstance.getBinding()){
				case NONE:
					TerraLogger.debug("No Binding");
					break;
				default:
					TerraLogger.debug("Bound - Remove from dropList");
					iter.remove();
					break;
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		
	}
	
	public int randInt(int min, int max){
		return rand.nextInt((max - min) + 1) + min;
	}
}
