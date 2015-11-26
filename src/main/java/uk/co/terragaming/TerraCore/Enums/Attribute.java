package uk.co.terragaming.TerraCore.Enums;

public enum Attribute {
	RANK("Rank"),
	STRENGTH("Strength"),
	AGILITY("Agility"),
	STAMINA("Stamina"),
	SPIRIT("Spirit"),
	RESISTANCE("Resistance"),
	INTELLECT("Intellect"),
	VITALITY("Vitality");
	
	private final String attribute;
	
	private Attribute(final String attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public String toString() {
		return attribute;
	}
}
