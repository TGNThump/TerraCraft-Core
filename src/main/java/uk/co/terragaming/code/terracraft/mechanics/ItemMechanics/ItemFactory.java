package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.util.List;

import uk.co.terragaming.code.terracraft.enums.ItemSource;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.DamageAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.MeleeDamageAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.RangedDamageAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.attributes.ResistanceAttribute;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.ItemBlock;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.ItemTrinket;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.armour.ArmourChest;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.armour.ArmourFeet;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.armour.ArmourHelm;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.armour.ArmourLegs;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.focus.FocusStaff;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.focus.FocusWand;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.tool.ToolAxe;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.tool.ToolHoe;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.tool.ToolPick;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.tool.ToolRod;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.tool.ToolShovel;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.weapon.WeaponMelee;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item.weapon.WeaponRanged;
import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemRegistry;
import uk.co.terragaming.code.terracraft.utils.MathUtils;

import com.google.common.collect.Lists;

public class ItemFactory {
	
	public static Item deserialize(SerializedItem data){
		
		String[] parts = data.getTypePath().split(".");
		if (!parts[0].equals("item")) return null;
		
		Item item = null;
		
		switch(parts[1]){
			case "weapon":
				DamageAttribute damage;
				switch (parts[2]){
					case "melee":
						item = new WeaponMelee(data.getItemSource());
						damage = (MeleeDamageAttribute) item.getAttribute(MeleeDamageAttribute.class);
						damage.setMinDamage(0);
						damage.setMaxDamage(0);
						break;
						
					case "ranged":
						item = new WeaponRanged(data.getItemSource());
						damage = (RangedDamageAttribute) item.getAttribute(RangedDamageAttribute.class);
						damage.setMinDamage(0);
						damage.setMaxDamage(0);
						break;
					default: return null;
				}
			case "armour":
				ResistanceAttribute resistance;
				switch (parts[2]){
					case "head":
						item = new ArmourHelm(data.getItemSource());
						resistance = (ResistanceAttribute) item.getAttribute(ResistanceAttribute.class);
						resistance.setResistance(0);
						break;
						
					case "chest":
						item = new ArmourChest(data.getItemSource());
						resistance = (ResistanceAttribute) item.getAttribute(ResistanceAttribute.class);
						resistance.setResistance(0);
						break;
						
					case "legs":
						item = new ArmourLegs(data.getItemSource());
						resistance = (ResistanceAttribute) item.getAttribute(ResistanceAttribute.class);
						resistance.setResistance(0);
						break;
						
					case "feet":
						item = new ArmourFeet(data.getItemSource());
						resistance = (ResistanceAttribute) item.getAttribute(ResistanceAttribute.class);
						resistance.setResistance(0);
						break;
					default: return null;
				}
			case "focus":
				switch (parts[2]){
					case "wand":
						item = new FocusWand(data.getItemSource());
						break;
						
					case "staff":
						item = new FocusStaff(data.getItemSource());
						break;
					default: return null;
				}
				break;
				
			case "tool":
				switch (parts[2]){
					case "pick":
						item = new ToolPick(data.getItemSource());
						break;
						
					case "axe":
						item = new ToolAxe(data.getItemSource());
						break;
						
					case "hoe":
						item = new ToolHoe(data.getItemSource());
						break;
						
					case "shovel":
						item = new ToolShovel(data.getItemSource());
						break;
						
					case "rod":
						item = new ToolRod(data.getItemSource());
						break;
					default: return null;
				}
				break;
			
			case "block":
				item = new ItemBlock(data.getIcon());
				break;
				
			case "trinket":
				item = new ItemTrinket(data.getItemSource());
				break;
			default: return null;
		}
		
		item.setId(data.getId());
		item.setAmount(1);
		item.setName(data.getName());
		item.setIcon(data.getIcon());
		item.setRarity(data.getRarity());
		item.setBindType(data.getBindType());
		item.setBinding(data.getBinding());
		item.setCurrentDurability(data.getCurDurability());
		item.setMaxDurability(data.getMaxDurability());
		item.setHistory(Lists.newArrayList());
		
		return item;
	}
	
	
	
	
	public static Item randomItem(){
		List<uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.Item> items = Lists.newArrayList(ItemRegistry.items.values());
		uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.Item i = items.get(MathUtils.randInt(0, items.size() - 1));
		
		Item item = null;
		
		switch (i.getItemClass()){
			case ARMOUR:
				item = new ArmourLegs(ItemSource.COMMAND);
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
