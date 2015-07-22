package uk.co.terragaming.code.terracraft.utils;


public class Assert {
	
	public static void notNull(Object object) throws IllegalArgumentException{
		if (object == null) throw new IllegalArgumentException();
	}
}
