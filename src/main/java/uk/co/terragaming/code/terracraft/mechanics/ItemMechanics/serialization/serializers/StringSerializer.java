package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;


public class StringSerializer extends Serializer<String>{

	@Override
	public String StringToObject(String value) {
		return value;
	}

	@Override
	public String ObjectToString(Object object) {
		return (String) object;
	}
	
	
}
