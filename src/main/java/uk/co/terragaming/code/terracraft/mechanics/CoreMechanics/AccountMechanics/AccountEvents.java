package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.net.InetAddress;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.exceptions.WhitelistException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics.WhitelistChecker;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class AccountEvents {
	
	private static AccountMechanics accountMechanics;
	private static AccountRegistry accountRegistry;
	
	public static void init(){
		accountMechanics = AccountMechanics.getInstance();
		accountRegistry = accountMechanics.getRegistry();
	}
	
	public static void onPreLogin(UUID uuid, InetAddress address, String name) throws WhitelistException{
		try{
			AccountSession session = SessionManager.getSession(uuid, address, name);
			if (session == null) return;
			Account account = AccountManager.getAccount(session);
			WhitelistChecker.checkBans(account);
			WhitelistChecker.checkPerms(account);
			SessionManager.updateSession(account, true);
			accountRegistry.addAccount(account, uuid);
			
			TerraLogger.info("Downloaded Account Data of %s.", account.getTerraTag());
		} catch (SQLException e){
			e.printStackTrace();
			throw new WhitelistException(Lang.get("internalException"));
		}
	}
	
	public static void onLogin(Player player){
		Account account = accountRegistry.getAccount(player);
		account.setPlayer(player);
	}
	
	public static void onLogout(Player player) throws WhitelistException{
		
		if (!accountRegistry.hasAccount(player.getUniqueId())) return;
		
		Account account = accountRegistry.getAccount(player);
		try {
			accountMechanics.getAccountsDao().update(account);
			SessionManager.updateSession(account, false);
			accountRegistry.removeAccount(account);
			TerraLogger.info("Uploaded Account Data of %s.", account.getTerraTag());
		} catch (SQLException e){
			e.printStackTrace();
			throw new WhitelistException(Lang.get(account.getLanguage(), "internalException"));
		}
	}
}
