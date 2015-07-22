package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;

import uk.co.terragaming.code.terracraft.enums.ItemBinding;


public class ItemBindingSerializer extends EnumSerializer<ItemBinding>{

	@Override
	public Class<? extends Enum<?>> getEnumClass() {
		return ItemBinding.class;
	}
	
}
