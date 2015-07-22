package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.tool;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.Item;


public abstract class ItemTool extends Item{

	public ItemTool() {
		super();
	}

	@Override
	public String getClassString() {
		return "Tool";
	}
	
}
