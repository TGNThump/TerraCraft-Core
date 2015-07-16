package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.armour;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.ResistanceAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.Item;

public abstract class ItemArmour extends Item{

	public ItemArmour(ItemSource itemSource) {
		super(itemSource);
		super.addAttribute(new ResistanceAttribute());
	}

	@Override
	public String getClassString() {
		return "Armour";
	}
	
}
