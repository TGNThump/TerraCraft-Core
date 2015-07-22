package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.GlobalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.LocalProperty;


public class DurabilityComponent extends ItemComponent{
	
	@GlobalProperty
	public Integer defaultDurability;
	
	@LocalProperty
	public Integer curDurability;
	
	@LocalProperty
	public Integer maxDurability;
	
	public DurabilityComponent(){
		
	}
	
	@Override
	public String render() {
		if (isBroken()){
			return "<grey><italic>Broken<r>";
		}
		return "";
	}
	
	public Integer getCurDurability(){
		if (curDurability == null) curDurability = defaultDurability;
		return curDurability;
	}
	
	public Integer getMaxDurability(){
		if (maxDurability == null) maxDurability = defaultDurability;
		return maxDurability;
	}
	
	public boolean isBroken(){
		return (getCurDurability() == 0);
	}
	
	public short getItemDurability(){
		short maxDurability = getItem().getIcon().getMaxDurability();
		if (isBroken()){ return maxDurability; }
		
		double maxDamage = getItem().getIcon().getMaxDurability();
		double percent = (double) getCurDurability() / getMaxDurability();
		
		if (percent > 1) percent = 1;
		if (percent < 0) percent = 0;
		
		double durability = (Math.round((1 - percent) * maxDamage));
		return (short) durability;		
	}
}
