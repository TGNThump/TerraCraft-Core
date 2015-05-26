package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;

public class ChannelManager {

	private static int id = 0;
	
	private static HashMap<Integer, Channel> channelsByID = new HashMap<>();
	private static HashMap<String, Integer> channelsByName = new HashMap<>();
	public static HashMap<UUID, List<String>> atPMNames = new HashMap<>();
	
	public static void addChannel(Channel channel, Integer id){
		channelsByID.put(id, channel);
		channelsByName.put(channel.getName(), id);
	}
	
	public static void addChannel(Channel channel){
		channel.setId(id);
		addChannel(channel, id);
		id++;
	}
	
	public static void removeChannel(Channel channel){
		channelsByID.remove(channel.getId());
		channelsByName.remove(channel.getName());
	}
	
	public static boolean hasChannel(Integer id){
		return channelsByID.containsKey(id);
	}
	
	public static boolean hasChannel(String name){
		return channelsByName.containsKey(name);
	}
	
	public static Channel getChannel(Integer id){
		return channelsByID.get(id);
	}
	
	public static Channel getChannel(String name){
		return getChannel(channelsByName.get(name));
	}
	
	public static Collection<Channel> getChannels(){
		return channelsByID.values();
	}
	
	public static Channel getChannel(Player sender, String name){
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
	
	public static Channel getWhisperChannel(Player sender, String recieverName, Player reciever, Integer nameLevel){
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
	
	public static String parseName(String name){
		return name.replace(" ", "_");
	}
	
	public static List<String> getAtTabList(Player sender){
		List<String> ret = Lists.newArrayList();
		
		for(Channel channel : ChannelManager.getChannels()){
			if (channel instanceof WhisperChannel) continue;
			if (channel instanceof PartyChannel) continue;
			if (!channel.canJoin(sender)) continue;
			ret.add("@" + channel.getDisplayName(sender));
		}
		
		for (List<String> names : ChannelManager.atPMNames.values()){
			for (String name : names){
				ret.add("@" + name);
			}
		}
		
		return ret;
	}
	
	public static List<String> getTabList(Player sender){
		List<String> ret = Lists.newArrayList();
		
		for(Channel channel : ChannelManager.getChannels()){
			if (channel instanceof WhisperChannel) continue;
			if (channel instanceof PartyChannel) continue;
			if (!channel.canJoin(sender)) continue;
			ret.add(channel.getDisplayName(sender));
		}
		
		return ret;
	}
}
