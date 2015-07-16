package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.weapon;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.RangedDamageAttribute;


public class WeaponRanged extends ItemWeapon{

	public WeaponRanged(ItemSource itemSource) {
		super(itemSource);
		super.addAttribute(new RangedDamageAttribute());
	}

	@Override
	public String getClassString() {
		return "Ranged Weapon";
	}
	
}
