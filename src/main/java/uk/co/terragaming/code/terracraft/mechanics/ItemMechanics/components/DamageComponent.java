package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;
import uk.co.terragaming.code.terracraft.utils.maths.MathUtils;


public class DamageComponent extends ItemComponent{
	
	Property<Integer> minDamage = get("minDamage", Integer.class);
	Property<Integer> maxDamage = get("maxDamage", Integer.class);
	
	public DamageComponent(){
		if (minDamage.get() == null) minDamage.set(0);
		if (maxDamage.get() == null) maxDamage.set(0);
	}
	
	// Overrides
	
	@Override
	public String render(Integer lvl) {
		if (lvl != 1) return "";
		return "<grey>" + getMinDamage() + " - " + getMaxDamage() + " Damage";
	}
	
	// Getters and Setters
	
	public Integer getDamage(){
		return MathUtils.randInt(getMinDamage(), getMaxDamage());
	}
	
	public Integer getMinDamage(){
 		return minDamage.get();
	}
	
	public Integer getMaxDamage(){
		return maxDamage.get();
	}
	
	public void setMinDamage(Integer minDamage){
		this.minDamage.set(minDamage);
	}
	
	public void setMaxDamage(Integer maxDamage){
		this.maxDamage.set(maxDamage);
	}
}
