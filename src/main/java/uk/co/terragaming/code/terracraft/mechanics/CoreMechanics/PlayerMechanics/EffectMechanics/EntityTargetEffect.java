package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityTargetEffect {
	
	public static void removeTargets(Player player) {
		for (Entity entity : player.getWorld().getEntities()) {
			if (entity instanceof Creature) {
				Creature creature = (Creature) entity;
				Entity t = creature.getTarget();
				Entity p = player;
				
				if (t == null) {
					continue;
				}
				if (p == null) {
					continue;
				}
				
				if (t.equals(p)) {
					((Creature) entity).setTarget(null);
				}
			}
		}
	}
}
