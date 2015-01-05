package uk.co.terragaming.code.terracraft.ItemMechanics;

public class ItemClass {
	public static final String MELEE = "Melee";
	public static final String RANGE = "Range";
	public static final String MAGIC = "Magic";
	public static final String ITEM = "Item";
	
	public static String getItemClass(String string) throws IllegalArgumentException {
		if (string.equals(MELEE)){
			return MELEE;
		} else if (string.equals(RANGE)){
			return RANGE;
		} else if (string.equals(MAGIC)){
			return MAGIC;
		} else if (string.equals(ITEM)){
			return ITEM;
		} else {
			throw new IllegalArgumentException();
		}
	}

}
