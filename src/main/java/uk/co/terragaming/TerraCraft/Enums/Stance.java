package uk.co.terragaming.TerraCraft.Enums;

public enum Stance {
	PEACE("PEACE"),
	NEUTRAL("NEUTRAL"),
	WAR("WAR");
	
	private final String stance;
	
	private Stance(final String stance){
		this.stance = stance;
	}
	
	@Override
	public String toString(){
		return stance;
	}
}