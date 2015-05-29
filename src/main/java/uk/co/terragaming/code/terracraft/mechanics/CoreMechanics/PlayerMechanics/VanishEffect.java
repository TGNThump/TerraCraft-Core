package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;

import com.google.common.collect.Lists;

public class VanishEffect {
	
	public static List<UUID> canSeeVanished = Lists.newArrayList();
	public static VanishPotionEffectManager potionManager = new VanishPotionEffectManager(TerraCraft.plugin);
	
	public static void vanishPlayer(Player vanishingPlayer) {
		vanishToggle(vanishingPlayer, true);
		potionManager.setGhost(vanishingPlayer, true);
	}
	
	public static void unvanishPlayer(Player vanishingPlayer) {
		vanishToggle(vanishingPlayer, false);
		potionManager.setGhost(vanishingPlayer, false);
	}
	
	public static void vanishToggle(Player vanishingPlayer, boolean vanishing) {
		for (final Player otherPlayer : TerraCraft.server.getOnlinePlayers()) {
			if (vanishingPlayer.equals(otherPlayer)) {
				continue;
			}
			
			if (vanishing) {
				if (!canSeeVanished.contains(otherPlayer.getUniqueId())) {
					if (otherPlayer.canSee(vanishingPlayer)) {
						otherPlayer.hidePlayer(vanishingPlayer);
					}
				}
			} else {
				if (!otherPlayer.canSee(vanishingPlayer)) {
					otherPlayer.showPlayer(vanishingPlayer);
				}
			}
			
		}
	}
	
	public static void vanishRefresh(Player player) {
		if (!VanishEffect.canSeeVanished.contains(player.getUniqueId())) {
			for (Player vanishedPlayer : Bukkit.getOnlinePlayers()) {
				if (PlayerEffects.hasEffect(vanishedPlayer, PlayerEffect.INVISIBLE)) {
					player.hidePlayer(vanishedPlayer);
				}
			}
		} else {
			for (Player vanishedPlayer : Bukkit.getOnlinePlayers()) {
				player.showPlayer(vanishedPlayer);
			}
		}
	}
}
