package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.enums.ItemRarity;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.GlobalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.LocalProperty;


public class RarityComponent extends ItemComponent{

	@GlobalProperty
	public ItemRarity defaultRarity;
	
	@LocalProperty
	public ItemRarity curRarity;
	
	public RarityComponent(){
		
	}
	
	@Override 
	public String render() {
		return "";
	}
	
	public ItemRarity getRarity(){
		if (curRarity == null) curRarity = defaultRarity;
		return curRarity;
	}
	
}
