package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ChatChannel;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.events.CharacterChangeEvent;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChatChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class ChatEventListener implements Listener{

	private HashMap<UUID, List<String>> atPMNames;
	private List<Entry<String, UUID>> atPMNamesReverse;
	private HashMap<String, ChatChannel> atChannelNames;
	
	public ChatEventListener(){
		atPMNames = new HashMap<>();
		atPMNamesReverse = Lists.newArrayList();
		atChannelNames = new HashMap<>();

		for(ChatChannel channel : ChatChannel.values()){
			atChannelNames.put(channel.toString(), channel);
		}
	}
	
	// Events
	
	@EventHandler
	public void onChatTab(PlayerChatTabCompleteEvent event){
		event.getChatMessage();
		if (event.getLastToken().startsWith("@") && event.getChatMessage().split(" ").length == 1){
			event.getTabCompletions().clear();
			Player player = event.getPlayer();
			List<String> tabList = ChatUtils.getFilteredTabList(getAtTabList(player), event.getLastToken());
			event.getTabCompletions().addAll(tabList);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat (AsyncPlayerChatEvent event){
		event.setCancelled(true);
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		Language lang = Language.ENGLISH;
		
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		if (registry.hasAccount(player)){
			lang = registry.getAccount(player).getLanguage();
		}
		
		if (message.startsWith("@")){
			
			String[] msgParts = message.split(" ");
			int parts = msgParts.length;
			
			String channelName = msgParts[0].substring(1);
			
			if (parts == 1){
				if (atChannelNames.containsKey(channelName)){
					ChatChannel channel = atChannelNames.get(channelName);
					ChatChannelManager.addToChannel(player, channel);
					ChatChannelManager.setDefaultChannel(player, channel);
					player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "ChatSetDefaultChannel"), channel.toString()));
				} else {
					player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "ChatInvalidChannel"), channelName));
				}
			} else {
				if (atChannelNames.containsKey(channelName)){
					ChatChannel channel = atChannelNames.get(channelName);
					ChatChannelManager.addToChannel(player, channel);
					sendMessageToChannel(player, channel, message.substring(message.indexOf(" ") + 1));
				} else {
					Player reciever = getPlayerFromName(channelName);
					if (reciever != null){
						sendMessageToPlayer(player, reciever, message.substring(message.indexOf(" ") + 1));
					}
				}
			}
			
		} else {
			sendMessageToChannel(player, ChatChannelManager.getDefaultChannel(player), event.getMessage());
		}
	}
	
	// Send Messages
	
	public static void sendMessageToChannel(Player sender, ChatChannel channel, String message){
	
		String name = getName(sender, channel);
		
		sender.sendMessage(Txt.parse("[<l>%s<r>] <%s> %s", channel.toString().substring(0, 1).toUpperCase(), name, message));
		
		for (Player player : Bukkit.getOnlinePlayers()){
			if (player.equals(sender)) continue;
			if (ChatChannelManager.isInChannel(player, channel)){
				
				player.sendMessage(Txt.parse("[<l>%s<r>] <%s> %s", channel.toString().substring(0, 1).toUpperCase(), name, message));
				
			}
		}
	}
	
	public static void sendMessageToPlayer(Player sender, Player reciever, String message){
		String yourName = getName(sender, null);
		String theirName = getName(reciever, null);
		sender.sendMessage(Txt.parse("<pink><Whisper to %s> %s<r>", theirName, message));
		reciever.sendMessage(Txt.parse("<pink><Whisper from %s> %s<r>", yourName, message));
	}
	
	private static String getName(Player sender, ChatChannel channel){
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		String name = sender.getName();
		
		if (registry.hasAccount(sender)){
			Account account = registry.getAccount(sender);
			name = account.getTerraTag();
			
			if (channel == null){
				Character character = account.getActiveCharacter();
				if (character != null){
					name = character.getName();
				}
			} else{
				if (!channel.equals(ChatChannel.OOC)){
					Character character = account.getActiveCharacter();
					if (character != null){
						name = character.getName();
					}
				}
			}			
		}
		return name;
	}
	
	// Events to keep Name List Up To Date
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event){
		UUID uuid = event.getPlayer().getUniqueId();
		
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				Player player = Bukkit.getPlayer(uuid);
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
				
				for (String name : names){
					atPMNamesReverse.add(new AbstractMap.SimpleEntry<String, UUID>(name, uuid));
				}
				
				atPMNames.put(uuid, names);
			}
			
		});
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event){
		UUID uuid = event.getPlayer().getUniqueId();
		
		ChatChannelManager.clearChannels(uuid);
		
		atPMNames.remove(uuid);

		for (Entry<String, UUID> entry : atPMNamesReverse){
			if (entry.getValue().equals(uuid)) atPMNamesReverse.remove(entry);
		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCharacterChange(CharacterChangeEvent event){
		
		UUID uuid = event.getPlayer().getUniqueId();
		
		ChatChannelManager.addToChannel(uuid, ChatChannel.LOCAL);
		ChatChannelManager.addToChannel(uuid, ChatChannel.GLOBAL);
		ChatChannelManager.addToChannel(uuid, ChatChannel.PARTY);
		ChatChannelManager.addToChannel(uuid, ChatChannel.EMOTE);
		ChatChannelManager.addToChannel(uuid, ChatChannel.YELL);
		ChatChannelManager.setDefaultChannel(uuid, ChatChannel.LOCAL);
		
		Character oldChar = event.getAccount().getActiveCharacter();
		Character newChar = event.getCharacter();
		
		List<String> names = atPMNames.get(uuid);
		if (oldChar != null){
			String oldName = parseName(oldChar.getName());
			names.remove(oldName);
			
			for (Iterator<Entry<String, UUID>> it = atPMNamesReverse.iterator(); it.hasNext();){
				Entry<String, UUID> entry = it.next();
				if (entry.getKey().equals(oldName)) it.remove();
			}
		}
		
		String newName = parseName(newChar.getName());
		
		names.add(newName);
		atPMNamesReverse.add(new AbstractMap.SimpleEntry<String, UUID>(newName, uuid));
	}
	
	// Util
	
	private String parseName(String name){
		return name.replace(" ", "_");
	}
	
	private List<String> getAtTabList(Player chatter){
		List<String> ret = Lists.newArrayList();
		
		for (String chanName : atChannelNames.keySet()){
			ret.add("@" + chanName);
		}
		
		for (List<String> names : atPMNames.values()){
			for (String name : names){
				ret.add("@" + name);
			}
		}
		
		return ret;
	}
	
	private UUID getPlayerUUIDFromName(String enteredName){
		
		enteredName = parseName(enteredName);
		
		for (Entry<String, UUID> entry : atPMNamesReverse){
			if (entry.getKey().equals(enteredName)){
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	private Player getPlayerFromName(String name){
		UUID uuid = getPlayerUUIDFromName(name);
		if (uuid == null) return null;
		return Bukkit.getPlayer(uuid);
	}
	
}
