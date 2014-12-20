package uk.co.terragaming.code.terracraft.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.PermissionGroup;

public class TerraLogger {

	public static void debug(Object msg){
		debug("" + msg, (Object) "");
	}
	
	public static void warn(String string) {
		warn(string, "");
	}
	
	public static void error(String string){
		error(string, "");
	}
	
	public static void info(String string) {
		info(string, "");
	}
	
	public static void debug(String string){
		debug(string, "");
	}
	
	public static void info(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "INFO" + ConsoleColor.WHITE + "] " + String.format(msg, args) + ConsoleColor.WHITE);
	}
	
	public static void debug(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "DEBUG" + ConsoleColor.WHITE + "] " + String.format(msg, args) + ConsoleColor.WHITE);
		
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		for(Player player : TerraCraft.Server().getOnlinePlayers()){
			Account account = registry.getAccount(player);
			
			for(PermissionGroup group : account.getGroupsAsArray()){
				if(group.getGroupName().toLowerCase().equals("developer")){
					player.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" + ChatColor.WHITE + "][" + ChatColor.DARK_AQUA + "DEBUG" + ChatColor.WHITE + "] " + String.format(msg, args) + ChatColor.RESET);
					break;
				}
			}
		}
	}
	
	public static void warn(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "WARN" + ConsoleColor.WHITE + "] " + ConsoleColor.RED + String.format(msg, args) + ConsoleColor.WHITE);
	}
	
	public static void error(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "ERROR" + ConsoleColor.WHITE + "] " + String.format(msg, args) + ConsoleColor.WHITE);
	}
	
	public static void blank(){
		System.out.println(" ");
	}
	
	public static class tools{
		public static String repeat(String str, int times){
			StringBuilder ret = new StringBuilder();
			for(int i=0; i<times; i++) ret.append(str);
			return ret.toString();
		}
	}
	
}
