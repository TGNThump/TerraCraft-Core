package uk.co.terragaming.code.terracraft.enums;

public enum ItemBinding {
	NONE("None"),
	ACCOUNT("Account"),
	CHARACTER("Character");
	
	private final String binding;
	
	ItemBinding(String binding){
		this.binding = binding;
	}

	public String getAttribute() {
		return binding;
	}
}