package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;

public class NickRegistry {

	private static final Map<UUID, String> nicks = new ConcurrentHashMap<>();
	
	public static boolean isNicked(UUID id) {
		return nicks.containsKey(id);
	}
	
	public static boolean isNickUsed(String nick){
		return nicks.containsValue(nick);
	}

	public static String getNick(UUID id) {
		return nicks.get(id);
	}
	
	public static void setNick(UUID id, String nick){
		if (nick.length() > 16) nick.substring(0, 15);
		if (isNicked(id)){
			removeNick(id);
		}
		
		nicks.put(id, nick);
		Nicks.setNick(id, nick);
	}
	
	public static void removeNick(UUID id){
		nicks.remove(id);
		Nicks.setNick(id, Bukkit.getPlayer(id).getName());
	}
	
	public static List<UUID> getPlayersWithNick(String nick) {
		List<UUID> list = new ArrayList<>();
		for (Entry<UUID, String> entry : nicks.entrySet()) {
			if (entry.getValue().equals(nick)) {
				list.add(entry.getKey());
			}
		}
		return Collections.unmodifiableList(list);
	}
	
	public static List<String> getUsedNicks() {
		return Collections.unmodifiableList(new ArrayList<>(nicks.values()));
	}	
}
