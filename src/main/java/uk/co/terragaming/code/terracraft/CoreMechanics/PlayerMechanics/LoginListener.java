package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import uk.co.terragaming.code.terracraft.ServerMode;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Exceptions.AccountBannedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Exceptions.AccountNotLinkedException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class LoginListener implements Listener{
	
	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event){
		
		if (TerraCraft.getServerMode().equals(ServerMode.LOADING)){
			event.disallow(Result.KICK_OTHER, ChatColor.AQUA + "TerraCraft " + TerraCraft.getServerName() + " is still Loading");
			return;
		}
		
		Account account = new Account(event.getUniqueId(), event.getAddress());
		AccountMechanics.getInstance().getRegistry().addAccount(account);
		
		try {
			account.onLogin();
		} catch (AccountNotLinkedException e) {
			event.disallow(Result.KICK_WHITELIST, PlayerMessages.get("account_not_linked"));
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			event.disallow(Result.KICK_OTHER, PlayerMessages.get("account_sql_exception"));
			return;
		} catch (AccountBannedException e) {
			event.disallow(Result.KICK_BANNED, e.getMessage());
			return;
		}
		TerraLogger.blank();
		TerraLogger.debug("Downloaded Data of " + account.getTerraTag());
	}
}
