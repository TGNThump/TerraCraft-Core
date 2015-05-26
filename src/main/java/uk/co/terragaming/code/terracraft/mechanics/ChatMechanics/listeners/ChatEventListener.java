package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.events.CharacterChangeEvent;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.Channel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.WhisperChannel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class ChatEventListener implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		event.setCancelled(true);
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		Player player = event.getPlayer();
		if (!registry.hasAccount(player)) return;
		Account account = registry.getAccount(player);
		String message = event.getMessage();
		
		if (message.startsWith("@")){
			String[] parts = message.split(" ");
			String channelName = parts[0].substring(1);
			message = message.substring(message.indexOf(" ") + 1);
			
			Channel channel = ChannelManager.getChannel(player, channelName);
			
			if (channel == null){
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatInvalidChannel"), channelName));
				return;
			}
			
			if (parts.length == 1){
				if (!channel.canJoin(player)){
					player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatNoChannelPerm"), channel.getDisplayName(player)));
					return;
				}
				channel.add(player);
				account.setActiveChannel(channel);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatSetDefaultChannel"), channel.getDisplayName(player)));
			} else {
				channel.add(player);
				channel.processChatEvent(player, message);
			}
		} else {
			Channel channel = account.getActiveChannel();
			if (channel == null){
				channel = ChannelManager.getChannel(0);
				account.setActiveChannel(channel);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatResetActiveChannel")));
				return;
			}
			if (!ChannelManager.hasChannel(channel.getId())){
				channel = ChannelManager.getChannel(0);
				account.setActiveChannel(channel);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "ChatResetActiveChannel")));
				return;
			}
			account.getActiveChannel().processChatEvent(player, message);
		}
	}
	
	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent event){
		if (event.getLastToken().startsWith("@") && event.getChatMessage().split(" ").length == 1){
			event.getTabCompletions().clear();
			Player player = event.getPlayer();
			List<String> tabList = ChatUtils.getFilteredTabList(ChannelManager.getAtTabList(player), event.getLastToken());
			event.getTabCompletions().addAll(tabList);
		}
	}
	
	@EventHandler
	public void onCharChange(CharacterChangeEvent event){
		Character oldChar = event.getAccount().getActiveCharacter();
		Character newChar = event.getCharacter();
		
		UUID uuid = event.getPlayer().getUniqueId();
		
		List<String> names = ChannelManager.atPMNames.get(uuid);
		
		if (oldChar != null){
			String name = ChannelManager.parseName(oldChar.getName());
			
			for (Channel channel : ChannelManager.getChannels()){
				if (channel instanceof WhisperChannel){
					WhisperChannel wChannel = (WhisperChannel) channel;
					if (wChannel.getJoinedPlayerNames().contains(name)){
						ChannelManager.removeChannel(wChannel);
						wChannel = null;
					}
				}
			}
			String oldName = ChannelManager.parseName(oldChar.getName());
			names.remove(oldName);
		}
		
		String newName = ChannelManager.parseName(newChar.getName());
		
		names.add(newName);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		Account account = AccountMechanics.getInstance().getRegistry().getAccount(player);
		
		for (Channel channel : ChannelManager.getChannels()){
			if (channel.isAutojoin()){
				channel.add(player);
			}
		}
		
		Channel local = ChannelManager.getChannel(0);
		account.setActiveChannel(local);
		
		UUID uuid = event.getPlayer().getUniqueId();
		List<String> names = Lists.newArrayList();
		
		String pName = ChannelManager.parseName(player.getName());
		
		names.add(pName);
		
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		if (registry.hasAccount(uuid)){
			String terraTag = ChannelManager.parseName(registry.getAccount(uuid).getTerraTag());
			if (!pName.equalsIgnoreCase(terraTag)){
				names.add(terraTag);
			}
		}
		
		ChannelManager.atPMNames.put(uuid, names);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		
		for (Channel channel : ChannelManager.getChannels()){
			if (channel instanceof WhisperChannel){
				if (channel.getJoinedPlayers().contains(uuid)){
					ChannelManager.removeChannel(channel);
					channel = null;
				}
			}
		}
		
		ChannelManager.atPMNames.remove(uuid);
	}
}
