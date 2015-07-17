package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.tool;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.Item;


public abstract class ItemTool extends Item{

	public ItemTool(ItemSource itemSource) {
		super(itemSource);
	}

	@Override
	public String getClassString() {
		return "Tool";
	}
	
}
