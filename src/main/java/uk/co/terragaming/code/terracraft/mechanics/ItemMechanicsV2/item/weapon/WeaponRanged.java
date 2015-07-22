package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.weapon;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.attributes.RangedDamageAttribute;


public class WeaponRanged extends ItemWeapon{

	public WeaponRanged() {
		super();
		super.addAttribute(new RangedDamageAttribute());
	}

	@Override
	public String getClassString() {
		return "Ranged Weapon";
	}
	
}
