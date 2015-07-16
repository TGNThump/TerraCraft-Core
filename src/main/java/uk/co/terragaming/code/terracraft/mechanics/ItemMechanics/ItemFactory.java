package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.util.List;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.MeleeDamageAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.ResistanceAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.armour.ArmourLeg;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.weapon.WeaponMelee;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.weapon.WeaponRanged;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemRegistry;
import uk.co.terragaming.code.terracraft.utils.MathUtils;

import com.google.common.collect.Lists;

public class ItemFactory {
	
	public static Item randomItem(){
		List<uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.Item> items = Lists.newArrayList(ItemRegistry.items.values());
		uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.Item i = items.get(MathUtils.randInt(0, items.size() - 1));
		
		Item item = null;
		
		switch (i.getItemClass()){
			case ARMOUR:
				item = new ArmourLeg(ItemSource.COMMAND);
				ResistanceAttribute resistance = (ResistanceAttribute) item.getAttribute(ResistanceAttribute.class);
				resistance.setResistance(i.getModResistance());
				break;
			case ITEM:
				break;
			case MAGIC:
				break;
			case MELEE:
				item = new WeaponMelee(ItemSource.COMMAND);
				MeleeDamageAttribute damage = (MeleeDamageAttribute) item.getAttribute(MeleeDamageAttribute.class);
				damage.setMinDamage(i.getMinBaseDamage());
				damage.setMaxDamage(i.getMaxBaseDamage());
				break;
			case RANGE:
				item = new WeaponRanged(ItemSource.COMMAND);
				break;
		}
		
		item.setBindType(i.getBindType());
		item.setCurrentDurability(i.getMaxDurability());
		item.setMaxDurability(i.getMaxDurability());
		item.setIcon(i.getMaterial());
		item.setName(i.getName());
		item.setRarity(i.getQuality());
		item.setTypeString(i.getType());
		
		return item;
	}
}
