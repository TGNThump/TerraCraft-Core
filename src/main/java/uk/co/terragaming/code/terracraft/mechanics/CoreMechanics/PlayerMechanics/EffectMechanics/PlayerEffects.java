package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.listeners.PlayerEffectListener;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;

public class PlayerEffects {
	
	private static HashMap<UUID, ArrayList<PlayerEffect>> effects;
	
	public static void init() {
		effects = new HashMap<>();
		Bukkit.getPluginManager().registerEvents(new PlayerEffectListener(), TerraCraft.plugin);
	}
	
	public static void addEffect(Player player, PlayerEffect effect) {
		if (effects == null) {
			TerraLogger.error("PlayerEffects no Initialized.");
			return;
		}
		
		if (hasEffect(player, effect)) return;
		
		if (effect.equals(PlayerEffect.INVISIBLE)) {
			EntityTargetEffect.removeTargets(player);
			VanishEffect.vanishPlayer(player);
		} else if (effect.equals(PlayerEffect.INVULNERABLE)) {
			EntityTargetEffect.removeTargets(player);
		} else if (effect.equals(PlayerEffect.NOMOVE)){
			player.setWalkSpeed(0.0f);
			player.setFlySpeed(0.0f);
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 128));
		}
		
		if (effects.containsKey(player.getUniqueId())) {
			effects.get(player.getUniqueId()).add(effect);
		} else {
			ArrayList<PlayerEffect> effectList = Lists.newArrayList();
			effectList.add(effect);
			effects.put(player.getUniqueId(), effectList);
		}
	}
	
	public static boolean hasEffect(Player player, PlayerEffect effect) {
		if (effects == null) {
			TerraLogger.error("PlayerEffects no Initialized.");
			return false;
		}
		if (!effects.containsKey(player.getUniqueId()))
			return false;
		return effects.get(player.getUniqueId()).contains(effect);
	}
	
	public static boolean hasEffect(UUID uuid, PlayerEffect effect) {
		if (effects == null) {
			TerraLogger.error("PlayerEffects no Initialized.");
			return false;
		}
		if (!effects.containsKey(uuid))
			return false;
		return effects.get(uuid).contains(effect);
	}
	
	public static void removeEffect(Player player, PlayerEffect effect) {
		if (effects == null) {
			TerraLogger.error("PlayerEffects no Initialized.");
			return;
		}
		if (!effects.containsKey(player.getUniqueId()))
			return;
		ArrayList<PlayerEffect> pEffects = effects.get(player.getUniqueId());
		if (!pEffects.contains(effect))
			return;
		
		if (effect.equals(PlayerEffect.INVISIBLE)) {
			VanishEffect.unvanishPlayer(player);
		} else if (effect.equals(PlayerEffect.NOMOVE)){
			player.removePotionEffect(PotionEffectType.JUMP);
			player.setWalkSpeed(0.2f);
			player.setFlySpeed(0.2f);
		}
		
		pEffects.remove(effect);
	}
	
	public static void clearEffects(Player player) {
		if (!effects.containsKey(player))
			return;
		for (PlayerEffect effect : effects.get(player.getUniqueId())) {
			removeEffect(player, effect);
		}
	}
	
}
