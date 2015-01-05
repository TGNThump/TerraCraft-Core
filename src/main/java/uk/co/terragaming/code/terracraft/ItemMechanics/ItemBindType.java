package uk.co.terragaming.code.terracraft.ItemMechanics;

public class ItemBindType {

	public static final String NONE = null;
	public static final String PICKUP = "Pickup";
	public static final String EQUIP = "Equip";
	public static final String ACCOUNT = "Accont";
	
	public static String getBindType(String string) {
		if(string == null){
			return NONE;
		}
		if (string.equals(PICKUP)){
			return PICKUP;
		} else if (string.equals(EQUIP)){
			return EQUIP;
		} else if (string.equals(ACCOUNT)){
			return ACCOUNT;
		} else {
			return NONE;
		}
	}

}
