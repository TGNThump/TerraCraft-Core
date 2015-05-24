package uk.co.terragaming.code.terracraft.enums;

public enum Language {
	ENGLISH("en"),
	NORWEGIAN("no"),
	ESTONIAN("et");
	
	private final String language;
	
	private Language(final String language){
		this.language = language;
	}
	
	@Override
	public String toString(){
		return language;
	}
}
