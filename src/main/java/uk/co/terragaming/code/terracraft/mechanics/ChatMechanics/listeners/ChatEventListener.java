package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.PartyChannel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.WhisperChannel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class ChatEventListener implements Listener{

	private HashMap<UUID, List<String>> atPMNames = new HashMap<>();
	
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
			
			Channel channel = getChannel(player, channelName);
			
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
			List<String> tabList = ChatUtils.getFilteredTabList(getAtTabList(player), event.getLastToken());
			event.getTabCompletions().addAll(tabList);
		}
	}
	
	@EventHandler
	public void onCharChange(CharacterChangeEvent event){
		Character oldChar = event.getAccount().getActiveCharacter();
		Character newChar = event.getCharacter();
		
		UUID uuid = event.getPlayer().getUniqueId();
		
		List<String> names = atPMNames.get(uuid);
		
		if (oldChar != null){
			String name = parseName(oldChar.getName());
			
			for (Channel channel : ChannelManager.getChannels()){
				if (channel instanceof WhisperChannel){
					WhisperChannel wChannel = (WhisperChannel) channel;
					if (wChannel.getJoinedPlayerNames().contains(name)){
						ChannelManager.removeChannel(wChannel);
						wChannel = null;
					}
				}
			}
			String oldName = parseName(oldChar.getName());
			names.remove(oldName);
		}
		
		String newName = parseName(newChar.getName());
		
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
		
		String pName = parseName(player.getName());
		
		names.add(pName);
		
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		if (registry.hasAccount(uuid)){
			String terraTag = parseName(registry.getAccount(uuid).getTerraTag());
			if (!pName.equalsIgnoreCase(terraTag)){
				names.add(terraTag);
			}
		}
		
		atPMNames.put(uuid, names);
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
		
		atPMNames.remove(uuid);
	}
	
	// Util
	
	public Channel getChannel(Player sender, String name){
		// If recognised channel, return it.
		if (ChannelManager.hasChannel(name)){
			return ChannelManager.getChannel(name);
		}
		
		// If its a party or guild channel
		if (name.equals("party") || name.equals("guild")){
			// TODO: DO stuff here...
			return null;
		}
		
		// If its a playerName / terratag / characterName,
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		for (Player player : Bukkit.getOnlinePlayers()){
			if (player.equals(sender)) continue;
			String playerName = player.getName();
			if (playerName.equals(name)){
				return getWhisperChannel(sender, playerName, player, 0);
			}
			
			if (!registry.hasAccount(player)) continue;
			
			Account account = registry.getAccount(player);
			String terraTag = account.getTerraTag();
			
			if (!terraTag.equalsIgnoreCase(playerName)){
				if (account.getTerraTag().equals(name)){
					return getWhisperChannel(sender, terraTag, player, 1); 
				}
			}
			
			Character character = account.getActiveCharacter();
			
			if (character == null) continue;
			String charName = parseName(character.getName());
			
			if (charName.equals(playerName) || charName.equals(terraTag)) continue;
			if (charName.equals(name)){
				return getWhisperChannel(sender, charName, player, 2); 
			}
		}
		
		return null;
	}
	
	public Channel getWhisperChannel(Player sender, String recieverName, Player reciever, Integer nameLevel){
		// If channel exists with name "whisper[senderName][recieverName]" or other way round, return it;
		// Otherwise, create a new whisperChannel "whisper[senderName][recieverName]" and return it;

		String senderName = ChatUtils.getName(sender, nameLevel);
		
		String channelName1 = "whisper" + sender.getName() + reciever.getName();
		String channelName2 = "whisper" + reciever.getName() + sender.getName();
		
		if (ChannelManager.hasChannel(channelName1)){
			return ChannelManager.getChannel(channelName1);
		}
		
		if (ChannelManager.hasChannel(channelName2)){
			return ChannelManager.getChannel(channelName2);
		}
		
		WhisperChannel channel = new WhisperChannel(sender, senderName, reciever, recieverName);
			channel.add(sender);
			channel.add(reciever);
			channel.setName(channelName1);
		ChannelManager.addChannel(channel);
		
		return channel;
	}
	
	private String parseName(String name){
		return name.replace(" ", "_");
	}
	
	private List<String> getAtTabList(Player sender){
		List<String> ret = Lists.newArrayList();
		
		for(Channel channel : ChannelManager.getChannels()){
			if (channel instanceof WhisperChannel) continue;
			if (channel instanceof PartyChannel) continue;
			if (!channel.canJoin(sender)) continue;
			ret.add("@" + channel.getDisplayName(sender));
		}
		
		for (List<String> names : atPMNames.values()){
			for (String name : names){
				ret.add("@" + name);
			}
		}
		
		return ret;
	}
}
