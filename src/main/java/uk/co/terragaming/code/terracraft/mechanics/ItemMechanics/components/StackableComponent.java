package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;


public class StackableComponent extends ItemComponent{

	Property<Integer> curStackSize = get("curStackSize", Integer.class);
	Property<Integer> maxStackSize = get("maxStackSize", Integer.class);
	
	public StackableComponent(){
		if (curStackSize.get() == null) curStackSize.set(1);
		if (maxStackSize.get() == null) maxStackSize.set(1);
	}
	
	// Getters and Setters
	
	public Integer getCurStackSize(){
		return curStackSize.get();
	}
	
	public void setCurStackSize(Integer size){
		curStackSize.set(size);
	}
	
	public Integer getMaxStackSize(){
		return maxStackSize.get();
	}
	
	public void setMaxStackSize(Integer max){
		maxStackSize.set(max);
	}
	
}
