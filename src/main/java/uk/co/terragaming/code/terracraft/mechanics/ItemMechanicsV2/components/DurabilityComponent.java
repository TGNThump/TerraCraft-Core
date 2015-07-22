package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.GlobalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.LocalProperty;


public class DurabilityComponent extends ItemComponent{
	
	@GlobalProperty
	private Integer defaultDurability;
	
	@LocalProperty
	private Integer curDurability;
	
	@LocalProperty
	private Integer maxDurability;
	
	public DurabilityComponent(){
		
	}
	
	@Override
	public String render() {
		return "";
	}
}
