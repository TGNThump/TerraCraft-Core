package uk.co.terragaming.code.terracraft.enums;

public enum ItemClass {
	MELEE("Melee"),
	RANGE("Range"),
	ARMOUR("Armour"),
	MAGIC("Magic"),
	ITEM("Item");

	private final String itemClass;
	
	ItemClass(String itemClass){
		this.itemClass = itemClass;
	}

	public String getItemClass() {
		return itemClass;
	}
	
	public static ItemClass getItemClass(String string) {
		if (string.equals("Melee")){
			return ItemClass.MELEE;
		} else if (string.equals("Range")){
			return ItemClass.RANGE;
		} else if (string.equals("Armour")){
			return ItemClass.ARMOUR;
		} else if (string.equals("Magic")){
			return ItemClass.MAGIC;
		} else if (string.equals("Item")){
			return ItemClass.ITEM;
		} else {
			throw new IllegalArgumentException();
		}
	}
}