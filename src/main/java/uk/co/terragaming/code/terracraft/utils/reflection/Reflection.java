package uk.co.terragaming.code.terracraft.utils.reflection;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class Reflection {
	
	public static String version = "";
	
	public static String getVersion() {
		if (!version.isEmpty()) return version;
		final String name = Bukkit.getServer().getClass().getPackage().getName();
		version = name.substring(name.lastIndexOf(".") + 1);
		return version;
	}
	
	public static Integer getVersionInt() {
		if (Reflection.getVersion().contains("1_7")) {
			return 170;
		}
		if (Reflection.getVersion().contains("1_8")) {
			return 180;
		}
		if (Reflection.getVersion().contains("1_8_R1")) {
			return 181;
		}
		if (Reflection.getVersion().contains("1_8_R2")) {
			return 182;
		}
		if (Reflection.getVersion().contains("1_8_R3")) {
			return 183;
		}
		return 170;
	}
	
	public static Field setAccessible(Field f) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
		return f;
	}

	public static Class<?> getClass(String classpath) {
		try {
			return Class.forName(String.format(classpath, getVersion()));
		} catch (ClassNotFoundException e) {
			TerraLogger.error("[<l>Reflection<red>] ClassNotFound: %s", classpath);
			return null;
		}
	}
}
