package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.weapon;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.attributes.MeleeDamageAttribute;


public class WeaponMelee extends ItemWeapon{

	public WeaponMelee() {
		super();
		super.addAttribute(new MeleeDamageAttribute());
	}

	@Override
	public String getClassString() {
		return "Melee Weapon";
	}
	
}
