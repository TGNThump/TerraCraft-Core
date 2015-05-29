package uk.co.terragaming.code.terracraft.enums;

public enum ItemClass {
	MELEE("Melee"),
	RANGE("Range"),
	ARMOUR("Armour"),
	MAGIC("Magic"),
	ITEM("Item");
	
	private final String itemClass;
	
	private ItemClass(final String itemClass) {
		this.itemClass = itemClass;
	}
	
	@Override
	public String toString() {
		return itemClass;
	}
}
