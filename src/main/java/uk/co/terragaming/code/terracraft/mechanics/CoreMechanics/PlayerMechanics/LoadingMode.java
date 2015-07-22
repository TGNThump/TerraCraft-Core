package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;


public class LoadingMode {
	
	public static void activeFor(Player player){
		PlayerEffects.addEffect(player, PlayerEffect.INVISIBLE);
		PlayerEffects.addEffect(player, PlayerEffect.INVULNERABLE);
		PlayerEffects.addEffect(player, PlayerEffect.NOCHAT);
		PlayerEffects.addEffect(player, PlayerEffect.NOMOVE);
		
		player.setGameMode(GameMode.SPECTATOR);
		player.setCanPickupItems(false);

		
	}
	
	public static void deactiveFor(Player player){
		PlayerEffects.removeEffect(player, PlayerEffect.INVISIBLE);
		PlayerEffects.removeEffect(player, PlayerEffect.INVULNERABLE);
		PlayerEffects.removeEffect(player, PlayerEffect.NOCHAT);
		PlayerEffects.removeEffect(player, PlayerEffect.NOMOVE);
		
		player.setGameMode(GameMode.SURVIVAL);
		player.setCanPickupItems(true);
	}
	
}
