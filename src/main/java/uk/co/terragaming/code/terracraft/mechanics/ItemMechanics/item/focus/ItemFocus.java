package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.focus;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.Item;


public abstract class ItemFocus extends Item{

	public ItemFocus(ItemSource itemSource) {
		super(itemSource);
	}

	@Override
	public String getClassString() {
		return "Focus";
	}
	
}
