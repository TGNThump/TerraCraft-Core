package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import java.util.HashMap;

import org.bukkit.ChatColor;

public class PlayerMessages {
	private static HashMap<String, String> messages = new HashMap<>();
	
	public static void load(){
		messages.put("account_not_linked", ChatColor.RED + "You have not linked this Minecraft Account to a Terra Gaming Network Account.");
		messages.put("account_sql_exception", ChatColor.RED + "The server could not connect to the TGN Core Database. Please Contact a Staff Member.");

		messages.put("account_banned_global", ChatColor.RED + "Your account has been GLOBALLY banned from all TGN Services.");
		messages.put("account_banned_perm", ChatColor.RED + "Your account has been PERMANENTLY banned from TerraCraft.");
		messages.put("account_access_revoked", ChatColor.RED + "Access to your Account has been revoked from this location.");
		
		messages.put("login_server_locked", ChatColor.RED + "This TerraCraft Server is currently LOCKED.");
	}
	
	public static String get(String key){
		return messages.get(key);
	}
}
