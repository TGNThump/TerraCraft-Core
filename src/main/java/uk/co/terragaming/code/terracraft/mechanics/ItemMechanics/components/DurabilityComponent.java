package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;


public class DurabilityComponent extends ItemComponent{

	Property<Integer> curDurability = get("curDurability", Integer.class);
	Property<Integer> maxDurability = get("maxDurability", Integer.class);
	
	@Override
	public String render(Integer lvl) {
		if (lvl != 2) return "";
		if (isBroken()){
			return "<grey><italic>Broken<r>";
		}
		return null;
	}
	
	public Integer getCurDurability(){
		return curDurability.get();
	}
	
	public Integer getMaxDurability(){
		return maxDurability.get();
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
	
	public void setCurDurability(Integer curDurability){
		this.curDurability.set(curDurability);
	}
	
	public void setMaxDurability(Integer maxDurability){
		this.maxDurability.set(maxDurability);
	}
	
}
