package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class Nicks {
	
	private static boolean enabled = false;
	private static Class<?> NicksClass;
	
	public static void init(){
		try {
			NicksClass = Class.forName("de.inventivegames.nickname.Nicks");
			enabled = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setNick(UUID uuid, String name){
		if (!enabled) return;
		try {
			NicksClass.getDeclaredMethod("setNick", UUID.class, String.class).invoke(null, uuid, name);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
}
