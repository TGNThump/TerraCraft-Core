package uk.co.terragaming.code.terracraft.enums;


public enum TCDebug {
	ITEMS("ITEMS"),
	WORLDS("WORLDS"),
	CHESTS("CHESTS");
	
	private final String type;
	
	private TCDebug(final String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;
	}
}
