package uk.co.terragaming.code.terracraft.utils;

public class StringTools {
	public static String toNormalCase(String string){
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
}
