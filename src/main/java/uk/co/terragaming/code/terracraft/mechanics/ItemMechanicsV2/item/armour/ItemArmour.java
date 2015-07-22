package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.armour;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.attributes.ResistanceAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.Item;

public abstract class ItemArmour extends Item{

	public ItemArmour() {
		super();
		super.addAttribute(new ResistanceAttribute());
	}

	@Override
	public String getClassString() {
		return "Armour";
	}
	
}
