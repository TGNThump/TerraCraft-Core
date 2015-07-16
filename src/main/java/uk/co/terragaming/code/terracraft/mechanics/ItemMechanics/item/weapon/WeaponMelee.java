package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.weapon;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.MeleeDamageAttribute;


public class WeaponMelee extends ItemWeapon{

	public WeaponMelee(ItemSource itemSource) {
		super(itemSource);
		super.addAttribute(new MeleeDamageAttribute());
	}

	@Override
	public String getClassString() {
		return "Melee Weapon";
	}
	
}
