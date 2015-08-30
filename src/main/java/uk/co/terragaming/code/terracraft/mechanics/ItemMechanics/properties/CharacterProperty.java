package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.utils.Assert;

public class CharacterProperty extends Property<Character>{

	private Character value;
	
	@Override
	public Character get() {
		return value;
	}

	@Override
	public void set(Character value) {
		this.value = value;
	}

	@Override
	public Object encode() {
		if (value == null) return null;
		return value.getId();
	}

	@Override
	public void decode(Object value) {
		this.value = null;
		if (value == null) return;
		Assert.ofType(value, Number.class);
		Integer id = ((Number) value).intValue();
		
		try {
			this.value = CharacterMechanics.getInstance().getCharacterDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
}
