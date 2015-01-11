package uk.co.terragaming.code.terracraft.enums;

public enum ItemBinding {
	NONE("None"),
	ACCOUNT("Account"),
	CHARACTER("Character");
	
	private final String binding;
	
	ItemBinding(String binding){
		this.binding = binding;
	}

	public String getBinding() {
		return binding;
	}
	
	public static ItemBinding getBinding(String string) {
		if (string == null){
			return ItemBinding.NONE;
		} else if (string.equals("Account")){
			return ItemBinding.ACCOUNT;
		} else if (string.equals("Character")){
			return ItemBinding.CHARACTER;
		} else {
			return ItemBinding.NONE;
		}
	}
}