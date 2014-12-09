package uk.co.terragaming.code.terracraft.utils;

public class TerraLogger {
	
	public static void debug(Object msg){
		debug("" + msg, (Object) "");
	}
	
	public static void debug(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "DEBUG" + ConsoleColor.WHITE + "] " + String.format(msg, args) + ConsoleColor.WHITE);
	}
	
	public static void debug(String player, String msg){
		debug(player, msg, "");
	}
	
	public static void debug(String player, String msg, Object args){
		
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft - DEBUG to " + player + ConsoleColor.WHITE + "]" + String.format(msg, args));
	}
	
	public static void info(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "INFO" + ConsoleColor.WHITE + "] " + String.format(msg, args) + ConsoleColor.WHITE);
	}
	

	public static void info(String string) {
		info(string, "");
	}
	
	public static void error(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "ERROR" + ConsoleColor.WHITE + "] " + String.format(msg, args) + ConsoleColor.WHITE);
	}
	
	public static void error(String string){
		error(string, "");
	}
	
	public static void warn(String msg, Object args){
		System.out.println("[" + ConsoleColor.CYAN + "TerraCraft" + ConsoleColor.WHITE + "][" + ConsoleColor.CYAN + "WARN" + ConsoleColor.WHITE + "] " + ConsoleColor.RED + String.format(msg, args) + ConsoleColor.WHITE);
	}

	public static void warn(String string) {
		warn(string, "");
	}

	
	public static void blank(){
		System.out.println(" ");
	}
	
	
	public static class tools{
		public static String repeat(String str, int times){
			StringBuilder ret = new StringBuilder();
			for(int i = 0;i < times;i++) ret.append(str);
			return ret.toString();
		}
	}
	
	
}