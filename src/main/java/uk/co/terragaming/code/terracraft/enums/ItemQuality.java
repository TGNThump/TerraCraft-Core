package uk.co.terragaming.code.terracraft.enums;

import org.bukkit.ChatColor;

public enum ItemQuality {
	POOR("POOR"),
	COMMON("COMMON"),
	UNCOMMON("UNCOMMON"),
	RARE("RARE"),
	EPIC("EPIC"),
	LEGENDARY("LEGENDARY"),
	ARTIFACT("ARTIFACT");

	private final String itemQuality;
	
	ItemQuality(String itemQuality){
		this.itemQuality = itemQuality;
	}

	public String getItemQuality() {
		return itemQuality;
	}
	
	public static ChatColor getChatColor(ItemQuality quality) {
		if (quality.equals(ItemQuality.POOR)){
			return ChatColor.GRAY;
		} else if (quality.equals(ItemQuality.COMMON)){
			return ChatColor.WHITE;
		} else if (quality.equals(ItemQuality.UNCOMMON)){
			return ChatColor.GREEN;
		} else if (quality.equals(ItemQuality.RARE)){
			return ChatColor.DARK_AQUA;
		} else if (quality.equals(ItemQuality.EPIC)){
			return ChatColor.LIGHT_PURPLE;
		} else if (quality.equals(ItemQuality.LEGENDARY)){
			return ChatColor.RED;
		} else if (quality.equals(ItemQuality.ARTIFACT)){
			return ChatColor.GOLD;
		}
		return ChatColor.WHITE;
	}

	public static ItemQuality getQuality(String string) {
		if(string == null){
			return null;
		}
		
		string = string.toUpperCase();
		if (string.equals("POOR")){
			return ItemQuality.POOR;
		} else if (string.equals("COMMON")){
			return ItemQuality.COMMON;
		} else if (string.equals("UNCOMMON")){
			return ItemQuality.UNCOMMON;
		} else if (string.equals("RARE")){
			return ItemQuality.RARE;
		} else if (string.equals("EPIC")){
			return ItemQuality.EPIC;
		} else if (string.equals("LEGENDARY")){
			return ItemQuality.LEGENDARY;
		} else if (string.equals("ARTIFACT")){
			return ItemQuality.ARTIFACT;
		}
		return null;
	}
}