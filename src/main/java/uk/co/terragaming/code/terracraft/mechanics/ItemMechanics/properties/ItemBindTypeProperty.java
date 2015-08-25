package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;


public class ItemBindTypeProperty extends EnumProperty<ItemBindType>{

	@Override
	protected Class<? extends Enum<?>> getEnumClass() {
		return ItemBindType.class;
	}
	
}
