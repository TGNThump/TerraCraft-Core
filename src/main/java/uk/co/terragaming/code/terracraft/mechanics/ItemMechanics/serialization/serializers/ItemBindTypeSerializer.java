package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;


public class ItemBindTypeSerializer extends EnumSerializer<ItemBindType>{

	@Override
	public Class<? extends Enum<?>> getEnumClass() {
		return ItemBindType.class;
	}
	
}
