package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.enums.ItemRarity;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;


public class RarityComponent extends ItemComponent{

	Property<ItemRarity> rarity = get("rarity", ItemRarity.class);
	
	public RarityComponent(){
		if (rarity.get() == null) rarity.set(ItemRarity.COMMON);
	}
	
	// Getters and Setters
	
	public ItemRarity getRarity(){
		return rarity.get();
	}
	
	public void setRarity(ItemRarity rarity){
		this.rarity.set(rarity);
	}
	
}
