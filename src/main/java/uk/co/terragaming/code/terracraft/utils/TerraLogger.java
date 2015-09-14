package uk.co.terragaming.code.terracraft.utils;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.GroupRegistry;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class TerraLogger {
	
	public static void debug(Object msg) {
		debug("" + msg, "");
	}
	
	public static void info(Object msg) {
		info("" + msg, "");
	}
	
	public static void warn(String string) {
		warn(string, "");
	}
	
	public static void error(String string) {
		error(string, "");
	}
	
	public static void info(String string) {
		info(string, "");
	}
	
	public static void debug(String string) {
		debug(string, "");
	}
	
	public static void info(String msg, Object... args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>INFO<r>] " + Txt.parse(Txt.parse(msg, true), args) + "<r>", true));
	}
	
	public static void debug(String msg, Object... args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>DEBUG<r>] " + Txt.parse(Txt.parse(msg, true), args) + "<r>", true));
		
		for (Player player : TerraCraft.server.getOnlinePlayers()) {
			Account account = AccountRegistry.getAccount(player);
			
			if (GroupRegistry.isInGroup(account, GroupRegistry.getGroup(2))) {
				player.sendMessage(Txt.parse("[<l>DEBUG<r>] " + String.format(Txt.parse(msg, false), args) + "<r>", false));
				break;
			}
		}
	}
	
	public static void warn(String msg, Object... args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>WARN<r>] " + String.format(Txt.parse(msg, true), args) + "<r>", true));
	}
	
	public static void error(String msg, Object... args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>ERROR<r>] " + String.format(Txt.parse(msg, true), args) + "<r>", true));
	}
	
	public static void blank() {
		System.out.println(" ");
	}
	
	public static class tools {
		
		public static String repeat(String str, int times) {
			StringBuilder ret = new StringBuilder();
			for (int i = 0; i < times; i++) {
				ret.append(str);
			}
			return ret.toString();
		}
	}
	
}