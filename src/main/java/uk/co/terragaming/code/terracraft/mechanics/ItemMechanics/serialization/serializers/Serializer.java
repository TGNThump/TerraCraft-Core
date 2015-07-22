package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;


public abstract class Serializer<E> {
	
	public abstract E StringToObject(String value) throws Exception;
	public abstract String ObjectToString(Object value) throws Exception;
}
