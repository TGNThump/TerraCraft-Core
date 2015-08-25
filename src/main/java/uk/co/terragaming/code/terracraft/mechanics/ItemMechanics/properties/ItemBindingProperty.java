package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import uk.co.terragaming.code.terracraft.enums.ItemBinding;


public class ItemBindingProperty extends EnumProperty<ItemBinding>{

	@Override
	protected Class<? extends Enum<?>> getEnumClass() {
		return ItemBinding.class;
	}
	
}
