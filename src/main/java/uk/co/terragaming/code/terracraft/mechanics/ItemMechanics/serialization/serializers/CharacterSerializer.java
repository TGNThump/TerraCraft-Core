package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;


public class CharacterSerializer extends Serializer<Character>{

	@Override
	public Character StringToObject(String value) {
		Integer charId;
		try {
			charId = new IntegerSerializer().StringToObject(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (charId == null) return null;
		try {
			return CharacterMechanics.getInstance().getCharacterDao().queryForId(charId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String ObjectToString(Object object) {
		if (object == null) return null;
		if (!(object instanceof Character)) return null;
		try {
			return (new IntegerSerializer().ObjectToString(((Character) object).getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
