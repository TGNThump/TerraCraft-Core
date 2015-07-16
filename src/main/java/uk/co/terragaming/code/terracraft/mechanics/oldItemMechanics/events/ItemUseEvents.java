package uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.oldItemMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.utils.MathUtils;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class ItemUseEvents implements Listener {
	
	private oldItemMechanics itemMechanics;
	private ItemInstanceRegistry registry;
	
	public ItemUseEvents() {
		itemMechanics = oldItemMechanics.getInstance();
		registry = itemMechanics.getItemInstanceRegistry();
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
		event.setDamage(MathUtils.randInt(item.getMinDamage(), item.getMaxDamage()));
		
		// ... and send a Debug Message.
		damager.sendMessage(Txt.parse("[<l>TerraCraft<r>] You dealt <gray>%s<r> damage to %s<r> using your %s<r>.", event.getDamage(), damagedName, item.getColouredName()));
	}
}
