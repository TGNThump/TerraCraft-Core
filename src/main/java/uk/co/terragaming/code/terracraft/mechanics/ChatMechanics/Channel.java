package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.event.player.AsyncPlayerChatEvent;


public abstract class Channel {

	private String name;
	private String tag;
	
	private Integer range;
	
	private ArrayList<UUID> joinedPlayers;
	
	public void addPlayer(UUID uuid){
		joinedPlayers.add(uuid);
	}
	
	public void removePlayer(UUID uuid){
		joinedPlayers.remove(uuid);
	}
	
	public boolean hasPlayer(UUID uuid){
		return joinedPlayers.contains(uuid);
	}
	
	public abstract void processChatEvent(AsyncPlayerChatEvent event);

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public Integer getRange() {
		return range;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setRange(Integer range) {
		this.range = range;
	}
}
