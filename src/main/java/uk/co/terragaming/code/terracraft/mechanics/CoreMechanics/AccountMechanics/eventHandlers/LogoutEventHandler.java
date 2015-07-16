package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.events.account.AccountLogoutEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.SessionManager;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;


public class LogoutEventHandler implements Listener{
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if (!AccountRegistry.hasAccount(player)) return;
		
		Account account = AccountRegistry.getAccount(player);
		
		try {
			AccountLogoutEvent e = new AccountLogoutEvent(account);
			Bukkit.getServer().getPluginManager().callEvent(e);
			
			AccountMechanics.getInstance().getAccountsDao().update(account);
			SessionManager.updateSession(account, false);
			AccountRegistry.removeAccount(account);
			
			TerraLogger.info("Uploaded Account Data of %s.", account.getTerraTag());
		} catch (Throwable e) {
			player.kickPlayer(Lang.get(account.getLanguage(), "internalException"));
			e.printStackTrace();
		}
		
	}
}
