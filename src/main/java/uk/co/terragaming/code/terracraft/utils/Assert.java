package uk.co.terragaming.code.terracraft.utils;


public class Assert {
	
	public static void notNull(Object object) throws IllegalArgumentException{
		if (object == null) throw new IllegalArgumentException();
	}
	
	public static void ofType(Object object, Class<?> type){
		notNull(object);
		notNull(type);
		if (!type.isAssignableFrom(object.getClass())){
			TerraLogger.error("<p>%s<r> is not assignable from <p>%s<r>", type.getSimpleName(), object.getClass().getSimpleName());
			throw new IllegalArgumentException();
		}
	}
}
