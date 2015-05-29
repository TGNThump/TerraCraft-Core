package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

public class AccountManager {
	
	private static Dao<Account, Integer> accountsDao;
	
	public static void init() {
		accountsDao = AccountMechanics.getInstance().getAccountsDao();
	}
	
	public static Account getAccount(AccountSession session) throws SQLException {
		Account account = accountsDao.queryForId(session.getAccount().getId());
		session.setAccount(account);
		account.setActiveSession(session);
		return account;
	}
	
}
