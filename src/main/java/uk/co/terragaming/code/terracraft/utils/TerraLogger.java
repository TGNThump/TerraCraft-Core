package uk.co.terragaming.code.terracraft.utils;

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
	
	public static void info(String msg, Object args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>INFO<r>] " + String.format(msg, args) + "<r>", true));
	}
	
	public static void debug(String msg, Object args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>DEBUG<r>] " + String.format(msg, args) + "<r>", true));
		
		// TODO: Redo Developer Account Debug Messages
		
		// AccountRegistry registry =
		// AccountMechanics.getInstance().getRegistry();
		//
		// for(Player player : TerraCraft.server.getOnlinePlayers()){
		// Account account = registry.getAccount(player);
		//
		// for(PermissionGroup group : account.getGroupsAsArray()){
		// if(group.getGroupName().toLowerCase().equals("developer")){
		// player.sendMessage("[" + ChatColor.DARK_AQUA + "TerraCraft" +
		// ChatColor.WHITE + "][" + ChatColor.DARK_AQUA + "DEBUG" +
		// ChatColor.WHITE + "] " + String.format(msg, args) + ChatColor.RESET);
		// break;
		// }
		// }
		// }
	}
	
	public static void warn(String msg, Object args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>INFO<r>] " + String.format(msg, args) + "<r>", true));
	}
	
	public static void error(String msg, Object args) {
		System.out.println(Txt.parse("[<l>TerraCraft<r>][<l>ERROR<r>] " + String.format(msg, args) + "<r>", true));
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