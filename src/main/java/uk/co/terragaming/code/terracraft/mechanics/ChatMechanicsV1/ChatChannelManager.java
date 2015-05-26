package uk.co.terragaming.code.terracraft.mechanics.ChatMechanicsV1;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import uk.co.terragaming.code.terracraft.enums.ChatChannel;

public class ChatChannelManager {
	
	private static HashMap<UUID, List<ChatChannel>> subscribedChannels = new HashMap<>();
	private static HashMap<UUID, ChatChannel> talkChannel = new HashMap<>();
	
	public static boolean isInChannel(UUID uuid, ChatChannel channel){
		if (!subscribedChannels.containsKey(uuid)) return false;
		return (subscribedChannels.get(uuid).contains(channel));
	}
	
	public static boolean isInChannel(Player player, ChatChannel channel){
		return isInChannel(player.getUniqueId(), channel);
	}
	
	public static List<ChatChannel> getChannels(UUID uuid){
		if (!subscribedChannels.containsKey(uuid)) return Lists.newArrayList();
		return subscribedChannels.get(uuid);
	}
	
	public static List<ChatChannel> getChannels(Player player){
		return getChannels(player.getUniqueId());
	}
	
	public static void addToChannel(UUID uuid, ChatChannel channel){
		if (subscribedChannels.containsKey(uuid)){
			subscribedChannels.get(uuid).add(channel);
		} else {
			List<ChatChannel> chan = Lists.newArrayList();
			chan.add(channel);
			subscribedChannels.put(uuid, chan);
		}
	}
	
	public static void addToChannel(Player player, ChatChannel channel){
		addToChannel(player.getUniqueId(), channel);
	}
	
	public static void removeFromChannel(UUID uuid, ChatChannel channel){
		if (!subscribedChannels.containsKey(uuid)) return;
		if (!subscribedChannels.get(uuid).contains(channel)) return;
		subscribedChannels.get(uuid).remove(channel);
	}
	
	public static void removeFromChannel(Player player, ChatChannel channel){
		removeFromChannel(player.getUniqueId(), channel);
	}
	
	public static ChatChannel getDefaultChannel(UUID uuid){
		return talkChannel.get(uuid);
	}
	
	public static ChatChannel getDefaultChannel(Player player){
		return getDefaultChannel(player.getUniqueId());
	}
	
	public static void setDefaultChannel(UUID uuid, ChatChannel channel){
		if (talkChannel.containsKey(uuid)) talkChannel.remove(uuid);
		talkChannel.put(uuid, channel);
	}
	
	public static void setDefaultChannel(Player player, ChatChannel channel){
		setDefaultChannel(player.getUniqueId(), channel);
	}
	
	public static void clearChannels(UUID uuid){
		if (!subscribedChannels.containsKey(uuid)) return;
		subscribedChannels.remove(uuid);
	}
	
	public static void clearChannels(Player player){
		clearChannels(player.getUniqueId());
	}
}
