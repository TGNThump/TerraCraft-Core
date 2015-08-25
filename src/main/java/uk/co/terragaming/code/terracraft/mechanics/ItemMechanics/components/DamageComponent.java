package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;
import uk.co.terragaming.code.terracraft.utils.MathUtils;


public class DamageComponent extends ItemComponent{
	
	Property<Integer> minDamage = get("minDamage", Integer.class);
	Property<Integer> maxDamage = get("maxDamage", Integer.class);
	
	@Override
	public String render(Integer lvl) {
		if (lvl != 1) return "";
		return "<grey>" + getMinDamage() + " - " + getMaxDamage() + " Damage";
	}
	
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
