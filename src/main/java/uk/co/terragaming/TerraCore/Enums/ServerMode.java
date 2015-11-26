package uk.co.terragaming.TerraCore.Enums;


public enum ServerMode {
	
	STARTING("STARTING"),
	STOPPING("STOPPING"),
	
	LOCKED("LOCKED"),
	DEVELOPMENT("DEVELOPMENT"),
	CLOSED_BETA("CLOSED_BETA"),
	OPEN_BETA("OPEN_BETA"),
	OPEN("OPEN"),
	PUBLIC("PUBLIC");
	
	private final String mode;
	
	private ServerMode(final String mode) {
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return mode;
	}
}
