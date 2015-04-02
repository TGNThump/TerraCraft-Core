package uk.co.terragaming.code.terracraft.CoreMechanics.ChatMechanics;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ChatListener implements Listener{
	
	@EventHandler
	public void onChat (AsyncPlayerChatEvent event){
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		Account account = registry.getAccount(event.getPlayer().getUniqueId());
		String group = account.getGroupsAsArray()[0].getGroupName().toLowerCase();
		if (group.equals("staff")){
			event.setFormat(ChatColor.DARK_AQUA + "<Staff><" + account.getTerraTag() + "> " + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		} else if (group.equals("developer")){
			event.setFormat(ChatColor.DARK_AQUA + "<Staff><" + account.getTerraTag() + "> " + ChatColor.WHITE + event.getMessage());
		} else if (group.equals("moderator")){
			event.setFormat(ChatColor.DARK_AQUA + "<Staff><" + account.getTerraTag() + "> " + ChatColor.WHITE + event.getMessage());
		} else if (group.equals("donator")){
			event.setFormat(ChatColor.GOLD + "<Donator>" + ChatColor.WHITE + "<" + account.getTerraTag() + "> " + event.getMessage());
		} else if (group.equals("member")){
			event.setFormat(ChatColor.DARK_GRAY + "<Member>" + ChatColor.WHITE + "<" + account.getTerraTag() + "> " + event.getMessage());
		} else if (group.equals("guest")){
			event.setFormat(ChatColor.GRAY + "<Guest>" + ChatColor.WHITE + "<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
		} else {
			TerraLogger.warn("Unknown Primary Rank: " + account.getGroupsAsArray()[0].getGroupName().toLowerCase());
			event.setFormat(ChatColor.DARK_GRAY + "<" + account.getGroupsAsArray()[0].getGroupName() + ">" + ChatColor.WHITE + "<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
		}
	}
}