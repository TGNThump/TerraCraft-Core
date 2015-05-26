package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import java.util.Collection;
import java.util.HashMap;

public class ChannelManager {

	private static int id = 0;
	
	private static HashMap<Integer, Channel> channelsByID = new HashMap<>();
	private static HashMap<String, Integer> channelsByName = new HashMap<>();
	
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
}
