package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;



public abstract class EnumSerializer<T extends Enum<T>> extends Serializer<Enum<?>>{

	public abstract Class<? extends Enum<?>> getEnumClass();
	
	@SuppressWarnings("unchecked")
	@Override
	public Enum<T> StringToObject(String value) throws Exception {
		if  (value == null) return null;
		return Enum.valueOf((Class<T>) getEnumClass(), value);
	}

	@Override
	public String ObjectToString(Object value) throws Exception {
		if  (value == null) return null;
		return ((Enum<?>) value).name();
	}

}
