package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.sql.SQLException;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.commands.AccountCommands;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers.LoginEventHandler;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers.LogoutEventHandler;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers.PingEventHandler;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers.PreLoginEventHandler;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;

import com.j256.ormlite.dao.Dao;

@MechanicParent("CoreMechanics")
@MechanicRequires("CoreMechanics.DatabaseMechanics")
public class AccountMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static AccountMechanics getInstance() {
		return (AccountMechanics) TerraCraft.getMechanic("CoreMechanics.AccountMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	
	private Dao<Account, Integer> accountsDao;
	private Dao<AccountSession, Integer> sessionsDao;
	
	// Mechanic Methods
	
	public Dao<Account, Integer> getAccountsDao() {
		return accountsDao;
	}
	
	public Dao<AccountSession, Integer> getSessionsDao() {
		return sessionsDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		new AccountRegistry();
	}

	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		accountsDao = (Dao<Account, Integer>) databaseMechanics.getDao(Account.class);
		sessionsDao = (Dao<AccountSession, Integer>) databaseMechanics.getDao(AccountSession.class);
		
		try {
			accountsDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		AccountManager.init();
		SessionManager.init();
		
		Bukkit.getPluginManager().registerEvents(new PingEventHandler(), TerraCraft.plugin);
		
		Bukkit.getPluginManager().registerEvents(new PreLoginEventHandler(), TerraCraft.plugin);
		Bukkit.getPluginManager().registerEvents(new LoginEventHandler(), TerraCraft.plugin);
		
		Bukkit.getPluginManager().registerEvents(new LogoutEventHandler(), TerraCraft.plugin);
		
		
		CommandRegistry.registerCommands(TerraCraft.plugin, new AccountCommands());
	}
	
	@Override
	public void PostInitialize() {
		
	}
	
	@Override
	public void PreDenitialize() {
		
	}
	
	@Override
	public void Denitialize() {
		
	}
	
	@Override
	public void PostDenitialize() {
		
	}
	
}
