package uk.co.terragaming.code.terracraft.enums;

import org.bukkit.ChatColor;

public enum ItemRarity {
	POOR("POOR"),
	COMMON("COMMON"),
	UNCOMMON("UNCOMMON"),
	RARE("RARE"),
	EPIC("EPIC"),
	LEGENDARY("LEGENDARY"),
	ARTIFACT("ARTIFACT");
	
	private final String quality;
	
	private ItemRarity(final String quality) {
		this.quality = quality;
	}
	
	@Override
	public String toString() {
		return quality;
	}
	
	public static ChatColor getChatColor(ItemRarity quality) {
		if (quality == null)
			return ChatColor.RED;
		if (quality.equals(ItemRarity.POOR))
			return ChatColor.DARK_GRAY;
		else if (quality.equals(ItemRarity.COMMON))
			return ChatColor.GRAY;
		else if (quality.equals(ItemRarity.UNCOMMON))
			return ChatColor.WHITE;
		else if (quality.equals(ItemRarity.RARE))
			return ChatColor.GREEN;
		else if (quality.equals(ItemRarity.EPIC))
			return ChatColor.LIGHT_PURPLE;
		else if (quality.equals(ItemRarity.LEGENDARY))
			return ChatColor.DARK_AQUA;
		else if (quality.equals(ItemRarity.ARTIFACT))
			return ChatColor.YELLOW;
		return ChatColor.WHITE;
	}
}
