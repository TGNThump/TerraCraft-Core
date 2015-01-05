package uk.co.terragaming.code.terracraft.ItemMechanics;

import org.bukkit.ChatColor;

public class ItemQuality {

	public static final String POOR = "POOR";
	public static final String COMMON = "COMMON";
	public static final String UNCOMMON = "UNCOMMON";
	public static final String RARE = "RARE";
	public static final String EPIC = "EPIC";
	public static final String LEGENDARY = "LEGENDARY";
	public static final String ARTIFACT = "ARTIFACT";
	
	public static String getQuality(String string) throws IllegalArgumentException {
		string = string.toUpperCase();
		if (string.equals(POOR)){
			return POOR;
		} else if (string.equals(COMMON)){
			return COMMON;
		} else if (string.equals(UNCOMMON)){
			return UNCOMMON;
		} else if (string.equals(RARE)){
			return RARE;
		} else if (string.equals(EPIC)){
			return EPIC;
		} else if (string.equals(LEGENDARY)){
			return LEGENDARY;
		} else if (string.equals(ARTIFACT)){
			return ARTIFACT;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public static ChatColor getChatColor(String quality) {
		if (quality.equals(POOR)){
			return ChatColor.GRAY;
		} else if (quality.equals(COMMON)){
			return ChatColor.WHITE;
		} else if (quality.equals(UNCOMMON)){
			return ChatColor.GREEN;
		} else if (quality.equals(RARE)){
			return ChatColor.DARK_AQUA;
		} else if (quality.equals(EPIC)){
			return ChatColor.LIGHT_PURPLE;
		} else if (quality.equals(LEGENDARY)){
			return ChatColor.RED;
		} else if (quality.equals(ARTIFACT)){
			return ChatColor.GOLD;
		}
		return ChatColor.WHITE;
	}

}
