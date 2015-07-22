package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;

import uk.co.terragaming.code.terracraft.enums.ItemRarity;


public class ItemRaritySerializer extends EnumSerializer<ItemRarity>{

	@Override
	public Class<? extends Enum<?>> getEnumClass() {
		return ItemRarity.class;
	}
	
}
