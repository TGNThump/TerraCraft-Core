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
	
	private final String quality;
	
	private ItemQuality(final String quality) {
		this.quality = quality;
	}
	
	@Override
	public String toString() {
		return quality;
	}
	
	public static ChatColor getChatColor(ItemQuality quality) {
		if (quality == null)
			return ChatColor.RED;
		if (quality.equals(ItemQuality.POOR))
			return ChatColor.DARK_GRAY;
		else if (quality.equals(ItemQuality.COMMON))
			return ChatColor.GRAY;
		else if (quality.equals(ItemQuality.UNCOMMON))
			return ChatColor.WHITE;
		else if (quality.equals(ItemQuality.RARE))
			return ChatColor.GREEN;
		else if (quality.equals(ItemQuality.EPIC))
			return ChatColor.LIGHT_PURPLE;
		else if (quality.equals(ItemQuality.LEGENDARY))
			return ChatColor.DARK_AQUA;
		else if (quality.equals(ItemQuality.ARTIFACT))
			return ChatColor.YELLOW;
		return ChatColor.WHITE;
	}
}
