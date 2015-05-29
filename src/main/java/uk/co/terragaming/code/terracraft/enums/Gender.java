package uk.co.terragaming.code.terracraft.enums;

public enum Gender {
	M("MALE"), F("FEMALE");
	
	private final String gender;
	
	private Gender(final String gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return gender;
	}
}
