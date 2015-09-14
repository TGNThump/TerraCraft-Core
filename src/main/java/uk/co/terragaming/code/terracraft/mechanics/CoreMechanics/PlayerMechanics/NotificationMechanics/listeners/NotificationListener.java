package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.terragaming.code.terracraft.events.character.CharacterJoinEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.Notification;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.NotificationManager;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class NotificationListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!AccountRegistry.hasAccount(player)) return;
		Account account = AccountRegistry.getAccount(player);
		
		for (Notification notification : NotificationManager.getNotifications(account)) {
			player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + notification.getMessage()));
			NotificationManager.remove(notification);
		}
	}
	
	@EventHandler
	public void onCharacterJoin(CharacterJoinEvent event) {
		Character character = event.getCharacter();
		Player player = character.getAccount().getPlayer();
		
		for (Notification notification : NotificationManager.getNotifications(character)) {
			player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + notification.getMessage()));
			NotificationManager.remove(notification);
		}
	}
}
