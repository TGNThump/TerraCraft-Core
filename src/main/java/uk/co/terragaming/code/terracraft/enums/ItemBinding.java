package uk.co.terragaming.code.terracraft.enums;

public enum ItemBinding {
	NONE(null), CHARACTER("Character"), ACCOUNT("Account");
	
	private final String binding;
	
	private ItemBinding(final String binding) {
		this.binding = binding;
	}
	
	@Override
	public String toString() {
		return binding;
	}
}
