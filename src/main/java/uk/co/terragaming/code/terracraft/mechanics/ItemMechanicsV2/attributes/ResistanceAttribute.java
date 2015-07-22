package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.attributes;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.ItemAttribute;

public class ResistanceAttribute extends ItemAttribute{
	
	private Integer resistance;
	
	@Override
	public String render() {
		String sign = (resistance > 0 ? "+" : (resistance < 0 ? "-" : ""));
		return "<gold> " + sign + resistance + " Resistance";
	}

	public Integer getResistance() {
		return resistance;
	}

	public void setResistance(Integer resistance) {
		this.resistance = resistance;
	}
}
