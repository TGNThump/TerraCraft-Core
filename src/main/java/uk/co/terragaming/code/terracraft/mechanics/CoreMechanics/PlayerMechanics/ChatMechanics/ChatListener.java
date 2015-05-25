package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.ChatMechanics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;

public class ChatListener implements Listener{
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat (AsyncPlayerChatEvent event){
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		Account account = registry.getAccount(event.getPlayer());
		
		event.setFormat("<" + account.getTerraTag() + "> " + event.getMessage());

//		String group = account.getGroupsAsArray()[0].getGroupName().toLowerCase();
//		if (group.equals("staff")){
//			event.setFormat(ChatColor.DARK_AQUA + "<Staff><" + account.getTerraTag() + "> " + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
//		} else if (group.equals("developer")){
//			event.setFormat(ChatColor.DARK_AQUA + "<Staff><" + account.getTerraTag() + "> " + ChatColor.WHITE + event.getMessage());
//		} else if (group.equals("moderator")){
//			event.setFormat(ChatColor.DARK_AQUA + "<Staff><" + account.getTerraTag() + "> " + ChatColor.WHITE + event.getMessage());
//		} else if (group.equals("donator")){
//			event.setFormat(ChatColor.GOLD + "<Donator>" + ChatColor.WHITE + "<" + account.getTerraTag() + "> " + event.getMessage());
//		} else if (group.equals("member")){
//			event.setFormat(ChatColor.DARK_GRAY + "<Member>" + ChatColor.WHITE + "<" + account.getTerraTag() + "> " + event.getMessage());
//		} else if (group.equals("guest")){
//			event.setFormat(ChatColor.GRAY + "<Guest>" + ChatColor.WHITE + "<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
//		} else {
//			TerraLogger.warn("Unknown Primary Rank: " + account.getGroupsAsArray()[0].getGroupName().toLowerCase());
//			event.setFormat(ChatColor.DARK_GRAY + "<" + account.getGroupsAsArray()[0].getGroupName() + ">" + ChatColor.WHITE + "<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
//		}
	}
}