package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.attributes;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.ItemAttribute;
import uk.co.terragaming.code.terracraft.utils.MathUtils;

public abstract class DamageAttribute extends ItemAttribute{
	
	private Integer minDamage;
	private Integer maxDamage;
	
	@Override
	public String render() {
		return "<grey>" + getMinDamage() + " - " + getMaxDamage() + " Damage";
	}

	public Integer getDamage(){
		return MathUtils.randInt(getMinDamage(), getMaxDamage());
	}
	
	// Getters and Setters
	
	public Integer getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(Integer minDamage) {
		this.minDamage = minDamage;
	}

	public Integer getMaxDamage() {
		return maxDamage;
	}
	
	public void setMaxDamage(Integer maxDamage) {
		this.maxDamage = maxDamage;
	}
	
	
}
