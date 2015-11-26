package uk.co.terragaming.TerraCore.Enums;

public enum BanType {
	TEMP("TEMP"),
	PERM("PERM"),
	GLOBAL("GLOBAL");
	
	private final String type;
	
	private BanType(final String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}