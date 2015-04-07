package uk.co.terragaming.code.terracraft.CoreMechanics.WhitelistMechanics;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.exceptions.AccountBannedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.exceptions.AccountNotLinkedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics.PlayerMessages;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class WhitelistLoginListener implements Listener{

	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event){
		
		if (TerraCraft.getServerMode().equals(ServerMode.LOADING)){
			// Server is still Loading
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "TerraCraft" + TerraCraft.getServerName() + " is still loading...");
			return;
			
		} else {
			try {
				Account account = new Account(event.getUniqueId(), event.getAddress());
				account.onPreLogin();
				
				TerraLogger.info("Downloaded Account Data of " + account.getTerraTag());
				
			} catch (AccountNotLinkedException e) {
				if (TerraCraft.getServerMode().equals(ServerMode.PUBLIC)){
					Account account = AccountMechanics.getInstance().getRegistry().getAccount(event.getUniqueId());
					account.setTerraTag(event.getName());
					TerraLogger.info("Non Bifrost Player " + event.getName() + " Joining...");
				} else {
					event.disallow(Result.KICK_WHITELIST, PlayerMessages.get("account_not_linked"));
					return;
				}
			} catch (AccountBannedException e) {
				event.disallow(Result.KICK_BANNED, e.getMessage());
				return;
			} catch (Exception e) {
				e.printStackTrace();
				event.disallow(Result.KICK_OTHER, PlayerMessages.get("account_sql_exception"));
				return;
			}
		}
	}
}
