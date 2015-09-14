package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.events.character.CharacterJoinEvent;
import uk.co.terragaming.code.terracraft.events.character.CharacterLeaveEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class CharacterJoinQuitMessages implements Listener{
	
	@EventHandler
	public void onCharacterJoin(CharacterJoinEvent event){
		Player player = event.getPlayer();
		Character character = event.getCharacter();
		Channel channel = ChannelManager.getChannel("localNotifications");
		channel.processChatEvent(player, Txt.parse("<n>" + character.getName() + "<r> has awoken from his slumber."));
	}
	
	@EventHandler
	public void onCharacterLeave(CharacterLeaveEvent event){
		Player player = event.getPlayer();
		Character character = event.getCharacter();
		if (!PlayerEffects.hasEffect(player, PlayerEffect.INVISIBLE)){
			Channel channel = ChannelManager.getChannel("localNotifications");
			channel.processChatEvent(player, Txt.parse("<n>" + character.getName() + "<r> has fallen into a deep sleep."));
		}
	}
}
