package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.events;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class ItemUseEvents implements Listener {
	
	private ItemMechanics itemMechanics;
	private ItemInstanceRegistry registry;
	private Random rand;
	
	public ItemUseEvents() {
		itemMechanics = ItemMechanics.getInstance();
		registry = itemMechanics.getItemInstanceRegistry();
		rand = new Random();
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		// If the Damager is a Player...
		if (!(event.getDamager() instanceof Player))
			return;
		Player damager = (Player) event.getDamager();
		
		if (PlayerEffects.hasEffect(damager, PlayerEffect.INVULNERABLE)) {
			event.setCancelled(true);
			return;
		}
		
		Entity damaged = event.getEntity();
		
		if (damaged instanceof Player) {
			if (PlayerEffects.hasEffect((Player) damaged, PlayerEffect.INVULNERABLE)) {
				event.setCancelled(true);
				return;
			}
		}
		
		// ... and the Item in hand is an TCItemInstance ...
		ItemStack is = damager.getItemInHand();
		if (!ItemManager.isItemInstance(is))
			return;
		Integer id = ItemManager.getItemInstanceId(is);
		ItemInstance item = registry.getItem(id);
		
		// ... Get the name of the Damaged Entity ...
		
		String damagedName;
		if (damaged.getCustomName() == null) {
			damagedName = "the " + ChatColor.BOLD + damaged.getType().toString().toLowerCase();
		} else if (damaged.getCustomName().isEmpty()) {
			damagedName = "the " + ChatColor.BOLD + damaged.getType().toString().toLowerCase();
		} else {
			damagedName = ChatColor.BOLD + damaged.getCustomName();
		}
		
		// ... Set the correct damage value ...
		event.setDamage(randInt(item.getMinDamage(), item.getMaxDamage()));
		
		// ... and send a Debug Message.
		damager.sendMessage(Txt.parse("[<l>TerraCraft<r>] You dealt <gray>%s<r> damage to %s<r> using your %s<r>.", event.getDamage(), damagedName, item.getColouredName()));
	}
	
	public int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
}
