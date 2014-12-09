package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;

public class LogoutListener implements Listener{

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		try{
			Account account = AccountMechanics.getAccountData(event.getPlayer().getUniqueId());
			account.onLogout();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
