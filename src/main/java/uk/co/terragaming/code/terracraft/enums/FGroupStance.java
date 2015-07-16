package uk.co.terragaming.code.terracraft.enums;


public enum FGroupStance {
	PEACE("PEACE"),
	NEUTRAL("NEUTRAL"),
	WAR("WAR");
	
	private final String stance;
	
	private FGroupStance(final String stance){
		this.stance = stance;
	}
	
	@Override
	public String toString(){
		return stance;
	}
}
