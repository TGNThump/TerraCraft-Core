package uk.co.terragaming.code.terracraft.enums;

public enum ItemBindType {
	NONE("NONE"), PICKUP("Pickup"), EQUIP("Equip"), ACCOUNT("Account");
	
	private final String bindType;
	
	private ItemBindType(final String bindType) {
		this.bindType = bindType;
	}
	
	@Override
	public String toString() {
		return bindType;
	}
}
