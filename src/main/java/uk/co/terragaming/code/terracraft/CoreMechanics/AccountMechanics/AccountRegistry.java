package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class AccountRegistry {
	private static HashMap<UUID, Account> accounts = new HashMap<UUID, Account>();
	
	public boolean hasAccount(UUID uuid){
		return(accounts.containsKey(uuid));
	}
	
	public boolean hasAccount(Account account){
		return(accounts.containsKey(account.getPlayerUUID()));
	}
	
	public Account getAccount(UUID uuid){
		return accounts.get(uuid);
	}
	
	public Account getAccount(Player player) {
		return accounts.get(player.getUniqueId());
	}
	
	public void addAccount(Account account){
		accounts.put(account.getPlayerUUID(), account);
	}
	
	public void removeAccount(UUID uuid){
		accounts.remove(uuid);
	}
	
	public void removeAccount(Account account){
		accounts.remove(account.getPlayerUUID());
	}
}
