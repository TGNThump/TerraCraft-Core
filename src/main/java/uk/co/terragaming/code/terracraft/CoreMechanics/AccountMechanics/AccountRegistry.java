package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class AccountRegistry {
	private static HashMap<Integer, Account> accounts1 = new HashMap<Integer, Account>();
	private static HashMap<UUID, Integer> accounts2 = new HashMap<UUID, Integer>();
	
	public boolean hasAccount(UUID uuid){
		return(accounts2.containsKey(uuid));
	}
	
	public boolean hasAccount(Account account){
		return(accounts2.containsKey(account.getPlayerUUID()));
	}
	
	public boolean hasAccount(Integer accountId){
		return(accounts1.containsKey(accountId));
	}
	
	public Account getAccount(UUID uuid){
		return accounts1.get(accounts2.get(uuid));
	}
	
	public Account getAccount(Player player) {
		return accounts1.get(accounts2.get(player.getUniqueId()));
	}
	
	public Account getAccount(Integer accountId){
		return accounts1.get(accountId);
	}
	
	public void addAccount(Account account){
		accounts2.put(account.getPlayerUUID(), account.getId());
		accounts1.put(account.getId(), account);
	}
	
	public void removeAccount(UUID uuid){
		accounts1.remove(accounts2.get(uuid));
		accounts2.remove(uuid);
	}
	
	public void removeAccount(Account account){
		removeAccount(account.getPlayerUUID());
	}
}
