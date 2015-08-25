package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;


public class StackableComponent extends ItemComponent{

	Property<Integer> curStackSize = get("curStackSize", Integer.class);
	Property<Integer> maxStackSize = get("maxStackSize", Integer.class);
	
	public Integer getCurStackSize(){
		return curStackSize.get();
	}
	
	public Integer getMaxStackSize(){
		return maxStackSize.get();
	}
	
}
