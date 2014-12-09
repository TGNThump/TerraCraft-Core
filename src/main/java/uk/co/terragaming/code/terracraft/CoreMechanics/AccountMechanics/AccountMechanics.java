package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import java.util.HashMap;
import java.util.UUID;

import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class AccountMechanics {
	public static HashMap<UUID, Account> accounts = new HashMap<UUID, Account>();
	
	public static boolean hasAccountData(UUID uuid){
		return accounts.containsKey(uuid);
	}
	
	public static Account getAccountData(UUID uuid){
		return accounts.get(uuid);
	}
	
	public static void addAccount(Account account){
		accounts.put(account.getPlayerUUID(), account);
	}

	public static void Initialize(){
		TerraLogger.info("  AccountMechanics Initialized");
	}
	
	public static void Denitalize(){
		
	}
}
