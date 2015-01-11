package uk.co.terragaming.code.terracraft.enums;

public enum CharacterAttribute {
	RANK("Rank"),
	STRENGTH("Strength"),
	AGILITY("Agility"),
	STAMINA("Stamina"),
	SPIRIT("Spirit"),
	RESISTANCE("Resistance"),
	INTELLECT("Intellect"),
	VITALITY("Vitality");
	
	private final String attribute;
	
	CharacterAttribute(String attribute){
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}
}