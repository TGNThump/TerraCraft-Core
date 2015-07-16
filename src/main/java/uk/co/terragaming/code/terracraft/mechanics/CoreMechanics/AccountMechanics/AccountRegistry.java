package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class AccountRegistry {
	
	private static AccountRegistry instance;
	
	private final HashMap<Integer, Account> accountsById;
	private final HashMap<UUID, Integer> accountsByUUID;
	
	public AccountRegistry() {
		instance = this;
		accountsById = new HashMap<>();
		accountsByUUID = new HashMap<>();
	}
	
	// Has Account
	
	public static boolean hasAccount(Integer id) {
		return instance.accountsById.containsKey(id);
	}
	
	public static boolean hasAccount(UUID uuid) {
		return instance.accountsByUUID.containsKey(uuid);
	}
	
	public static boolean hasAccount(Account account) {
		return hasAccount(account.getId());
	}
	
	public static boolean hasAccount(Player player) {
		return hasAccount(player.getUniqueId());
	}
	
	// Get Account
	
	public static Account getAccount(Integer id) {
		return instance.accountsById.get(id);
	}
	
	public static Account getAccount(UUID uuid) {
		return getAccount(instance.accountsByUUID.get(uuid));
	}
	
	public static Account getAccount(Player player) {
		return getAccount(player.getUniqueId());
	}
	
	// Add Account
	
	public static void addAccount(Account account, Integer id, UUID uuid) {
		instance.accountsById.put(id, account);
		instance.accountsByUUID.put(uuid, id);
	}
	
	public static void addAccount(Account account, UUID uuid) {
		addAccount(account, account.getId(), uuid);
	}
	
	public static void addAccount(Account account) {
		addAccount(account, account.getId(), account.getPlayer().getUniqueId());
	}
	
	// Remove Account
	
	public static void removeAccount(Integer id, UUID uuid) {
		instance.accountsById.remove(id);
		instance.accountsByUUID.remove(uuid);
	}
	
	public static void removeAccount(Account account, UUID uuid) {
		removeAccount(account.getId(), uuid);
	}
	
	public static void removeAccount(Account account) {
		removeAccount(account.getId(), account.getPlayer().getUniqueId());
	}
	
}
