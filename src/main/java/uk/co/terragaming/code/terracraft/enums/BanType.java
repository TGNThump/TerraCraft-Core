package uk.co.terragaming.code.terracraft.enums;

public enum BanType {
	TEMP("TEMP"),
	PERM("PERM"),
	GLOBAL("GLOBAL");
	
	private final String type;
	
	private BanType(final String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;
	}
}
