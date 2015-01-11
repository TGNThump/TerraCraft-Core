package uk.co.terragaming.code.terracraft.enums;

public enum ItemBindType {
	NONE(null),
	PICKUP("Pickup"),
	EQUIP("Equip"),
	ACCOUNT("Account");

	private final String binding;
	
	ItemBindType(String binding){
		this.binding = binding;
	}

	public String getAttribute() {
		return binding;
	}

	public static ItemBindType getBindType(String string) {
		if (string == null){
			return ItemBindType.NONE;
		} else if (string.equals("Pickup")){
			return ItemBindType.PICKUP;
		} else if (string.equals("Equip")){
			return ItemBindType.EQUIP;
		} else if (string.equals("Account")){
			return ItemBindType.ACCOUNT;
		} else {
			return ItemBindType.NONE;
		}
	}
}