package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.net.InetAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.exceptions.WhitelistException;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.j256.ormlite.dao.Dao;

public class SessionManager {
	
	private static Dao<AccountSession, Integer> sessionsDao;
	
	public static void init() {
		sessionsDao = AccountMechanics.getInstance().getSessionsDao();
	}
	
	public static AccountSession getSession(UUID uuid, InetAddress address, String name) throws SQLException, WhitelistException {
		
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("serviceId", 3);
		conditions.put("userAgent", "Minecraft (" + uuid.toString() + ")");
		List<AccountSession> sessions = sessionsDao.queryForFieldValues(conditions);
		
		for (AccountSession session : sessions) {
			if (session.getIpAddress().equals(address.toString().substring(1))) {
				if (!session.isEnabled())
					throw new WhitelistException(Lang.get(Language.ENGLISH, "accountAccessRevoked"));
				return session;
			}
		}
		
		AccountSession session = new AccountSession();
		
		if (sessions.size() > 0) {
			session.setAccount(sessions.get(0).getAccount());
		} else if (TerraCraft.serverMode.equals(ServerMode.PUBLIC)) {
			TerraLogger.info("Non Bifrost Player '" + name + "' Joining...");
		} else
			throw new WhitelistException(Lang.get(Language.ENGLISH, "accountNotLinked"));
		
		session.setServiceId(3);
		session.setIpAddress(address);
		session.setUserAgent("Minecraft (" + uuid.toString() + ")");
		session.setFirstUsed(DateTime.now());
		session.setEnabled(true);
		
		return session;
	}
	
	public static void updateSession(Account account, boolean active) throws SQLException {
		AccountSession session = account.getActiveSession();
		updateSession(session, active);
	}
	
	public static void updateSession(AccountSession session, boolean active) throws SQLException {
		Account account = session.getAccount();
		session.setActive(active);
		if (active) {
			session.setLastUsed(DateTime.now());
		}
		if (!account.getSessions().contains(session)) {
			account.getSessions().add(session);
		}
		sessionsDao.createOrUpdate(session);
	}
	
}
