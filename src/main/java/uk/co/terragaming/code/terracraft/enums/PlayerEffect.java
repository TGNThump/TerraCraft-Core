package uk.co.terragaming.code.terracraft.enums;

public enum PlayerEffect {
	INVULNERABLE("INVULNERABLE"),
	INVISIBLE("INVISIBLE"),
	STAFFMODE("STAFFMODE"),
	NOCHAT("NOCHAT"),
	NOMOVE("NOMOVE"),
	NOINTERACT("NOINTERACT"),
	LEAVING("LEAVING");
	
	private final String effect;
	
	private PlayerEffect(final String effect) {
		this.effect = effect;
	}
	
	@Override
	public String toString() {
		return effect;
	}
}
