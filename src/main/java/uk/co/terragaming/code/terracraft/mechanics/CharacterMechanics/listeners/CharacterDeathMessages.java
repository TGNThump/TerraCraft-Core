package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.NPCMechanics.NPCMechanics;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterDeathMessages implements Listener	{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event){
		event.setDeathMessage(null);
		
		Channel channel = ChannelManager.getChannel("deaths");
		Player victim = event.getEntity();
		if (victim.getLastDamageCause() == null) return;
		if (NPCMechanics.isNPC(victim)) return;
		if (PlayerEffects.hasEffect(victim, PlayerEffect.INVISIBLE)) return;
		
		String deathMessage = getDeathMessage(victim, channel);
		
		channel.processChatEvent(event.getEntity(), Txt.parse(deathMessage));
	}
	
	public String getDeathMessage(Player victim, Channel channel){
		String victimName = ChatUtils.getName(victim, channel);
		Entity killer = victim.getKiller();
		
		if (killer == null && victim.getLastDamageCause() instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) victim.getLastDamageCause();
			killer = e.getDamager();
		}
		
		String killerName = "";

		switch(victim.getLastDamageCause().getCause()){
			case BLOCK_EXPLOSION:
				return "<i>" + victimName + "<b>'s body was found splattered on the ground.";
			case CONTACT:
				return "<i>" + victimName + "<b> was pricked to death.";
			case DROWNING:
				return "<i>" + victimName + "<b> was found floating in a pool of water.";
			case ENTITY_ATTACK:
				if (killer instanceof Player){
					killerName = ChatUtils.getName((Player) killer, channel);
				} else {
					if (killer.getCustomName() == null) {
						killerName = "<b>the <i>" + killer.getType().toString().toLowerCase();
					} else if (killer.getCustomName().isEmpty()) {
						killerName = "<b>the <i>" + killer.getType().toString().toLowerCase();
					} else {
						killerName = ChatColor.BOLD + killer.getCustomName();
					}
				}
				
				return "<i>" + victimName + "<b> was murdered by <i>" + killerName + "<b>.";
			case ENTITY_EXPLOSION:
				if (killer instanceof Player){
					
					if (((Player) killer).equals(victim)){
						return "<i>" + victimName + "<b> blew themself up.";
					}
					
					killerName = ChatUtils.getName((Player) killer, channel);
				} else {
					if (killer.getCustomName() == null) {
						killerName = "<b>the <i>" + killer.getType().toString().toLowerCase();
					} else if (killer.getCustomName().isEmpty()) {
						killerName = "<b>the <i>" + killer.getType().toString().toLowerCase();
					} else {
						killerName = ChatColor.BOLD + killer.getCustomName();
					}
				}
				
				return "<i>" + victimName + "<b> was blown up by <i>" + killerName + "<b>.";
			case FALL:
				return "<i>" + victimName + "<b> fell to their death.";
			case FALLING_BLOCK:
				return "<i>" + victimName + "<b> was crushed by a falling block.";
			case FIRE:
				return "<i>" + victimName + "<b> burned to death.";
			case FIRE_TICK:
				return "<i>" + victimName + "<b> burned to death.";
			case LAVA:
				return "<i>" + victimName + "<b> fell into the lava.";
			case LIGHTNING:
				return "<i>" + victimName + "<b> was struck by lightning.";
			case MAGIC:
				return "<i>" + victimName + "<b> was killed by magic.";
			case MELTING:
				return "";
			case POISON:
				return "<i>" + victimName + "<b> was poisoned.";
			case PROJECTILE:
				return "<i>" + victimName + "<b> was found with an arrow lodged in his head.";
			case STARVATION:
				return "<i>" + victimName + "<b> burned to death.";
			case SUFFOCATION:
				return "<i>" + victimName + "<b> suffocated in a block.";
			case SUICIDE:
				return "<i>" + victimName + "<b> commited suicide.";
			case THORNS:
				return "<i>" + victimName + "<b> was found dead.";
			case VOID:
				return "<i>" + victimName + "<b> died.";
			case WITHER:
				return "<i>" + victimName + "<b> died.";
			default:
				return "<i>" + victimName + "<b> was found dead.";
		}
	}
}
