package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item;

import uk.co.terragaming.code.terracraft.enums.ItemSource;


public class ItemTrinket extends Item{

	public ItemTrinket(ItemSource itemSource) {
		super(itemSource);
		super.setTypeString("");
	}

	@Override
	public String getClassString() {
		return "Trinket";
	}
	
}
