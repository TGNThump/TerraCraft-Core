package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class AccountRegistry {
	
	private final HashMap<Integer, Account> accountsById;
	private final HashMap<UUID, Integer> accountsByUUID;
	
	public AccountRegistry() {
		accountsById = new HashMap<>();
		accountsByUUID = new HashMap<>();
	}
	
	// Has Account
	
	public boolean hasAccount(Integer id) {
		return accountsById.containsKey(id);
	}
	
	public boolean hasAccount(UUID uuid) {
		return accountsByUUID.containsKey(uuid);
	}
	
	public boolean hasAccount(Account account) {
		return hasAccount(account.getId());
	}
	
	public boolean hasAccount(Player player) {
		return hasAccount(player.getUniqueId());
	}
	
	// Get Account
	
	public Account getAccount(Integer id) {
		return accountsById.get(id);
	}
	
	public Account getAccount(UUID uuid) {
		return getAccount(accountsByUUID.get(uuid));
	}
	
	public Account getAccount(Player player) {
		return getAccount(player.getUniqueId());
	}
	
	// Add Account
	
	public void addAccount(Account account, Integer id, UUID uuid) {
		accountsById.put(id, account);
		accountsByUUID.put(uuid, id);
	}
	
	public void addAccount(Account account, UUID uuid) {
		addAccount(account, account.getId(), uuid);
	}
	
	public void addAccount(Account account) {
		addAccount(account, account.getId(), account.getPlayer().getUniqueId());
	}
	
	// Remove Account
	
	public void removeAccount(Integer id, UUID uuid) {
		accountsById.remove(id);
		accountsByUUID.remove(uuid);
	}
	
	public void removeAccount(Account account, UUID uuid) {
		removeAccount(account.getId(), uuid);
	}
	
	public void removeAccount(Account account) {
		removeAccount(account.getId(), account.getPlayer().getUniqueId());
	}
	
}
