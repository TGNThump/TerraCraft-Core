package uk.co.terragaming.TerraCore.Enums;

import java.util.Locale;

public enum Language {
	ENGLISH(Locale.ENGLISH);
	
	private final Locale locale;
	
	private Language(final Locale language) {
		this.locale = language;
	}
	
	@Override
	public String toString() {
		return locale.toString();
	}
}