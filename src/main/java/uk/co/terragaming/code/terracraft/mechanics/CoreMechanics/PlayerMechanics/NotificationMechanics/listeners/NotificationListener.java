package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.events.CharacterChangeEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.Notification;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.NotificationManager;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class NotificationListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		if (!registry.hasAccount(player)) return;
		Account account = registry.getAccount(player);
		
		for (Notification notification : NotificationManager.getNotifications(account)){
			player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + notification.getMessage()));
			NotificationManager.remove(notification);
		}
	}
	
	@EventHandler
	public void onCharacterChange(CharacterChangeEvent event){
		Player player = event.getPlayer();
		Character character = event.getCharacter();
		
		for (Notification notification : NotificationManager.getNotifications(character)){
			player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + notification.getMessage()));
			NotificationManager.remove(notification);
		}
	}
}
