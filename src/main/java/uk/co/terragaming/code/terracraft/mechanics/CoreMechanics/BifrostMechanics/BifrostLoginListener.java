package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.BifrostMechanics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerLoginEvent;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountSession;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.SessionManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.j256.ormlite.dao.Dao;

public class BifrostLoginListener implements Listener {
	
	private Dao<AccountSession, Integer> sessionsDao;

	public BifrostLoginListener() {
		DatabaseMechanics databaseMechanics = DatabaseMechanics.getInstance();
		sessionsDao = (Dao<AccountSession, Integer>) databaseMechanics.getDao(AccountSession.class);
	}
	
	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		
		if (TerraCraft.serverMode.equals(ServerMode.LOADING)) {
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "TerraCraft" + TerraCraft.getServerName() + " is still loading...");
			return;
		} else if (TerraCraft.serverMode.equals(ServerMode.SHUTDOWN)) {
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "TerraCraft" + TerraCraft.getServerName() + " is shutting down...");
			return;
		} else {
			// Catch SQL Exceptions
			try {
				
				// Try Getting Session
				AccountSession session = null;
				try {
					session = SessionManager.getSession(event.getUniqueId(), event.getAddress(), event.getName());
				} catch (TerraException e) {}
				
				if (session == null) {
					event.allow();
					return;
				}
				
				event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("bifrostAllreadyLinked")));
				return;
				
			} catch (SQLException e) {
				e.printStackTrace();
				event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("accountInternalError")));
				return;
			}
			
		}
	}
	
	@EventHandler
	public void PlayerLoginEvent(PlayerLoginEvent event) {
		try {
			String userAgent = "Minecraft (" + event.getPlayer().getUniqueId().toString() + ")";
			String hash = event.getHostname().substring(0, event.getHostname().indexOf("."));
			
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("serviceId", 3);
			conditions.put("hash", hash);
			conditions.put("enabled", false);
			List<AccountSession> sessions = sessionsDao.queryForFieldValues(conditions);
			
			if (sessions.size() < 1) {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Txt.parse(Lang.get("bifrostLinkFail")));
				return;
			}
			
			AccountSession session = sessions.get(0);
			session.setUserAgent(userAgent);
			session.setEnabled(true);
			session.setFirstUsed(DateTime.now());
			session.setLastUsed(DateTime.now());
			session.setHash(null);
			sessionsDao.update(session);
			
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Txt.parse(Lang.get("bifrostLinkSuccess")));
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Txt.parse(Lang.get("accountInternalError")));
			return;
		}
	}
}
