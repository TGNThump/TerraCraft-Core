package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.VanishEffect;

public class PlayerEffectListener implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if (!PlayerEffects.hasEffect(player, PlayerEffect.INVULNERABLE))
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		if (!(event.getTarget() instanceof Player))
			return;
		Player player = (Player) event.getTarget();
		if (!PlayerEffects.hasEffect(player, PlayerEffect.INVISIBLE) || !PlayerEffects.hasEffect(player, PlayerEffect.INVULNERABLE))
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (PlayerEffects.hasEffect(event.getPlayer(), PlayerEffect.INVISIBLE)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		VanishEffect.potionManager.addPlayer(player);
		
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				VanishEffect.vanishRefresh(player);
			}
		});
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		VanishEffect.potionManager.removePlayer(player);
	}
}
