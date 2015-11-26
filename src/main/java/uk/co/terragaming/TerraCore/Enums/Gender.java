package uk.co.terragaming.TerraCore.Enums;


public enum Gender {
	M("MALE"),
	F("FEMALE"),
	O("OTHER");
	
	private final String gender;
	
	private Gender(final String gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return gender;
	}
}
