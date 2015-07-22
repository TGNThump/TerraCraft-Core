package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers;

import java.net.InetAddress;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.events.account.AccountPreLoginEvent;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountSession;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.SessionManager;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;


public class PreLoginEventHandler implements Listener{
	
	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event){
		try {
			
			// If the account is a new account and the server is allowing connections ...
			if (AccountRegistry.hasAccount(event.getUniqueId())) return;
			if (!serverAllowingConnections(event)) return;
			
			// ... grab the player metadata ...
			UUID uuid = event.getUniqueId();
			InetAddress address = event.getAddress();
			String name = event.getName();
			
			// ... and the players session and account ...
			AccountSession session = SessionManager.getSession(uuid, address, name);
			if (!checkSession(session)) return;
			Account account = AccountManager.getAccount(session);

			// ... call the AccountPreLoginEvent ...
			AccountPreLoginEvent e = new AccountPreLoginEvent(account);
			Bukkit.getServer().getPluginManager().callEvent(e);
			if (e.isCancelled()){
				// ... and kick if its cancelled ...
				event.disallow(Result.KICK_OTHER, Txt.parse(e.getKickMessage()));
				return;
			}
			
			// ... otherwise, update the session and add the account to the registry ...
			SessionManager.updateSession(session, true);
			AccountRegistry.addAccount(account, uuid);
			
			TerraLogger.info("Downloaded Account Data of <n>%s<r>.", account.getTerraTag());
			
			event.allow();
		} catch (TerraException e){
			event.disallow(Result.KICK_OTHER, Txt.parse(e.getMessages().get(0)));
		} catch (SQLException e1) {
			e1.printStackTrace();
			event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("internalException")));
		}
	}
	
	private boolean serverAllowingConnections(AsyncPlayerPreLoginEvent event){
		if (TerraCraft.serverMode.equals(ServerMode.LOADING)) {
			event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "LOADING"));
			return false;
		}
		
		if (TerraCraft.serverMode.equals(ServerMode.SHUTDOWN)){
			event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "SHUTTING DOWN"));
			return false;
		}
		
		if (TerraCraft.serverMode.equals(ServerMode.BIFROST)){
			event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "LOCKED IN BIFROST REG MODE"));
			return false;
		}
		
		return true;
	}
	
	private boolean checkSession(AccountSession session){
		return (session != null);
	}
	
}
