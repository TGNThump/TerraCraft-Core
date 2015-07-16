package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import uk.co.terragaming.code.terracraft.events.account.AccountLoginEvent;
import uk.co.terragaming.code.terracraft.events.account.AccountLogoutEvent;
import uk.co.terragaming.code.terracraft.events.character.CharacterJoinEvent;
import uk.co.terragaming.code.terracraft.events.character.CharacterLeaveEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.NotificationChannel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.WhisperChannel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class ChatEventListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		if (!AccountRegistry.hasAccount(player))
			return;
		Account account = AccountRegistry.getAccount(player);
		String message = event.getMessage();
		
		if (message.startsWith("@")) {
			String[] parts = message.split(" ");
			String channelName = parts[0].substring(1);
			message = message.substring(message.indexOf(" ") + 1);
			
			Channel channel = ChannelManager.getChannel(player, channelName);
			
			if (channel instanceof NotificationChannel)channel = null;
			
			if (channel == null) {
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatInvalidChannel"), channelName));
				return;
			}
			
			if (parts.length == 1) {
				if (!channel.canJoin(player)) {
					player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatNoChannelPerm"), channel.getDisplayName(player)));
					return;
				}
				channel.add(player);
				account.setActiveChannel(channel);
				if (channel.getId() == 0) {
					ChannelManager.getChannel("yell").add(player);
					ChannelManager.getChannel("emote").add(player);
				}
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatSetDefaultChannel"), channel.getDisplayName(player)));
			} else {
				channel.add(player);
				channel.processChatEvent(player, message);
			}
		} else {
			Channel channel = account.getActiveChannel();
			if (channel == null) {
				channel = ChannelManager.getChannel(0);
				account.setActiveChannel(channel);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatResetActiveChannel")));
				return;
			}
			if (!ChannelManager.hasChannel(channel.getId())) {
				channel = ChannelManager.getChannel(0);
				account.setActiveChannel(channel);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatResetActiveChannel")));
				return;
			}
			if (!channel.contains(account)) {
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "chatNoChannels")));
				return;
			}
			account.getActiveChannel().processChatEvent(player, message);
		}
	}
	
	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent event) {
		if (event.getLastToken().startsWith("@") && event.getChatMessage().split(" ").length == 1) {
			event.getTabCompletions().clear();
			Player player = event.getPlayer();
			List<String> tabList = ChatUtils.getFilteredTabList(ChannelManager.getAtTabList(player), event.getLastToken());
			event.getTabCompletions().addAll(tabList);
		}
	}
	
	@EventHandler
	public void onCharacterLeave(CharacterLeaveEvent event){
		Character character = event.getCharacter();
		UUID uuid = event.getPlayer().getUniqueId();
		List<String> names = ChannelManager.atPMNames.get(uuid);
		
		String name = ChannelManager.parseName(character.getName());
		
		for (Channel channel : ChannelManager.getChannels()) {
			if (channel instanceof WhisperChannel) {
				WhisperChannel wChannel = (WhisperChannel) channel;
				if (wChannel.getJoinedPlayerNames().contains(name)) {
					ChannelManager.removeChannel(wChannel);
					wChannel = null;
				}
			}
		}
		
		names.remove(name);
		
	}
	
	@EventHandler
	public void onCharacterJoin(CharacterJoinEvent event){
		Character character = event.getCharacter();
		UUID uuid = event.getPlayer().getUniqueId();
		List<String> names = ChannelManager.atPMNames.get(uuid);
		
		String name = ChannelManager.parseName(character.getName());
		character.getAccount().setActiveChannel(ChannelManager.getChannel(0));
		names.add(name);
	}
	
//	@EventHandler
//	public void onCharacterJoin(CharacterChangeEvent event) {
//		Character oldChar = event.getAccount().getActiveCharacter();
//		Character newChar = event.getCharacter();
//		
//		UUID uuid = event.getPlayer().getUniqueId();
//		
//		List<String> names = ChannelManager.atPMNames.get(uuid);
//		
//		if (oldChar != null) {
//			String name = ChannelManager.parseName(oldChar.getName());
//			
//			for (Channel channel : ChannelManager.getChannels()) {
//				if (channel instanceof WhisperChannel) {
//					WhisperChannel wChannel = (WhisperChannel) channel;
//					if (wChannel.getJoinedPlayerNames().contains(name)) {
//						ChannelManager.removeChannel(wChannel);
//						wChannel = null;
//					}
//				}
//			}
//			String oldName = ChannelManager.parseName(oldChar.getName());
//			names.remove(oldName);
//		} else {
//			event.getAccount().setActiveChannel(ChannelManager.getChannel(0));
//		}
//		
//		String newName = ChannelManager.parseName(newChar.getName());
//		
//		names.add(newName);
//	}
	
	@EventHandler
	public void onAccountLogin(AccountLoginEvent event) {
		Account account = event.getAccount();
		Player player = account.getPlayer();
		
		for (Channel channel : ChannelManager.getChannels()) {
			if (channel.isAutojoin()) {
				channel.add(player);
			}
		}
		
		Channel local = ChannelManager.getChannel(0);
		account.setActiveChannel(local);
		
		UUID uuid = player.getUniqueId();
		List<String> names = Lists.newArrayList();
		
		String pName = ChannelManager.parseName(player.getName());
		
		names.add(pName);
		
		
		if (AccountRegistry.hasAccount(uuid)) {
			String terraTag = ChannelManager.parseName(AccountRegistry.getAccount(uuid).getTerraTag());
			if (!pName.equalsIgnoreCase(terraTag)) {
				names.add(terraTag);
			}
		}
		
		ChannelManager.atPMNames.put(uuid, names);
	}
	
	@EventHandler
	public void onAccountLogout(AccountLogoutEvent event) {
		Account account = event.getAccount();
		Player player = account.getPlayer();
		UUID uuid = player.getUniqueId();
		
		for (Channel channel : ChannelManager.getChannels()) {
			if (channel instanceof WhisperChannel) {
				if (channel.getJoinedPlayers().contains(uuid)) {
					ChannelManager.removeChannel(channel);
					channel = null;
				}
			}
		}
		
		ChannelManager.atPMNames.remove(uuid);
	}
}
