package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.exceptions.WhitelistException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountEvents;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class LogoutListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (!AccountMechanics.getInstance().getRegistry().hasAccount(event.getPlayer().getUniqueId()))
			return;
		
		try {
			AccountEvents.onLogout(event.getPlayer());
		} catch (WhitelistException e) {
			event.getPlayer().kickPlayer(Txt.parse(e.getMessages().get(0)));
		}
	}
	
}
