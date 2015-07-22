package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.ItemBlock;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.ItemTrinket;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.armour.ArmourChest;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.armour.ArmourFeet;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.armour.ArmourHelm;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.armour.ArmourLegs;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.focus.FocusStaff;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.focus.FocusWand;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.tool.ToolAxe;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.tool.ToolHoe;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.tool.ToolPick;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.tool.ToolRod;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.tool.ToolShovel;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.weapon.WeaponMelee;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item.weapon.WeaponRanged;


public class ItemFactory2 {
	
	public static Item deserialize(SerializedItem data){
		Item item = getBaseItem(data.getTypePath());
		
		item.setId(data.getId());
		item.setName(data.getName());
		item.setIcon(data.getIcon());
		
		//item.setOwner(data.getOwner());
		//item.setSlot(data.getSlotId());
		item.setAmount(1);
		
		item.setRarity(data.getRarity());
		item.setBindType(data.getBindType());
		item.setBinding(data.getBinding());
		
		item.setCurrentDurability(data.getCurDurability());
		item.setMaxDurability(data.getMaxDurability());
		
		item.setSource(data.getItemSource());
		
		return item;
	}
	
	public static Item getBaseItem(String typePath){
		
		String[] parts = typePath.split(".");
		if (!parts[0].equals("item")) return null;
		
		switch(parts[1]){
			case "weapon":
				switch (parts[2]){
					case "melee":	return new WeaponMelee();
					case "ranged": 	return new WeaponRanged();
					default: return null;
				}
				
			case "armour":
				switch (parts[2]){
					case "head":	return new ArmourHelm();
					case "chest":	return new ArmourChest();
					case "legs":	return new ArmourLegs();
					case "feet":	return new ArmourFeet();
					default: return null;
				}
				
			case "focus":
				switch (parts[2]){
					case "wand":	return new FocusWand();
					case "staff":	return new FocusStaff();
					default: return null;
				}

				
			case "tool":
				switch (parts[2]){
					case "pick":	return new ToolPick();
					case "axe":		return new ToolAxe();
					case "hoe":		return new ToolHoe();
					case "shovel":	return new ToolShovel();	
					case "rod":		return new ToolRod();
					default: return null;
				}
				
			
			case "block": return new ItemBlock();
			case "trinket": return new ItemTrinket();
			default: return null;
		}
	}
}
