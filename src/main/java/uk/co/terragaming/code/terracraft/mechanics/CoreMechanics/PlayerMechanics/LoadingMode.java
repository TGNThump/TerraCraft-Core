package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.utils.reflection.ActionBar;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class LoadingMode {
	
	public static HashMap<UUID, Integer> loading = new HashMap<>();
	public static BukkitTask task;
	
	public static void activeFor(Player player){
		PlayerEffects.addEffect(player, PlayerEffect.INVISIBLE);
		PlayerEffects.addEffect(player, PlayerEffect.INVULNERABLE);
		PlayerEffects.addEffect(player, PlayerEffect.NOCHAT);
		PlayerEffects.addEffect(player, PlayerEffect.NOMOVE);
		PlayerEffects.addEffect(player, PlayerEffect.NOINTERACT);
		
		player.setGameMode(GameMode.SPECTATOR);
		player.setCanPickupItems(false);
		
		loading.put(player.getUniqueId(), 1);

		if (task == null && TerraCraft.plugin.isEnabled()){
			task = Bukkit.getScheduler().runTaskTimer(TerraCraft.plugin, new Runnable(){

				@Override
				public void run() {
					if (loading.isEmpty()){
						task.cancel();
						task = null;
						return;
					}
					for (UUID uuid : loading.keySet()){
						Player player = Bukkit.getPlayer(uuid);
						Integer dots = loading.get(uuid);
						if (dots > 3){
							dots = 1;
							loading.put(uuid, 1);
						}
						ActionBar.sendMessage(player, "<l>Loading" + Txt.repeat(".", dots));
						dots++;
						loading.put(uuid, dots);
					}
				}
				
			}, 0, 10);
		}
	}
	
	public static void deactiveFor(Player player){
		loading.remove(player.getUniqueId());
		
		player.setGameMode(GameMode.SURVIVAL);
		player.setCanPickupItems(true);
		
		PlayerEffects.removeEffect(player, PlayerEffect.INVISIBLE);
		PlayerEffects.removeEffect(player, PlayerEffect.INVULNERABLE);
		PlayerEffects.removeEffect(player, PlayerEffect.NOCHAT);
		PlayerEffects.removeEffect(player, PlayerEffect.NOMOVE);
		PlayerEffects.removeEffect(player, PlayerEffect.NOINTERACT);
	}
	
	public static boolean isActiveFor(Player player){
		return loading.containsKey(player.getUniqueId());
	}
	
}
