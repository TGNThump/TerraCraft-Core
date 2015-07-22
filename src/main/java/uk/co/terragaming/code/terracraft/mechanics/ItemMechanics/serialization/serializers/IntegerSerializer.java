package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;


public class IntegerSerializer extends Serializer<Integer> {

	@Override
	public Integer StringToObject(String value) throws Exception{
		if  (value == null) return null;
		return Integer.parseInt(value);
	}

	@Override
	public String ObjectToString(Object object) throws Exception{
		if  (object == null) return null;
		return Integer.toString((Integer) object);
	}
	
}
