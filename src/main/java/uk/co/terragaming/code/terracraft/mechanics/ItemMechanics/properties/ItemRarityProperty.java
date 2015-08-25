package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import uk.co.terragaming.code.terracraft.enums.ItemRarity;


public class ItemRarityProperty extends EnumProperty<ItemRarity>{

	@Override
	protected Class<? extends Enum<?>> getEnumClass() {
		return ItemRarity.class;
	}
	
}
