package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class WhisperChannel extends Channel{

	private UUID you;
	private UUID them;
	
	private String yourName;
	private String theirName;
	
	public WhisperChannel(Player you, String yourName, Player them, String theirName){
		this.setTag("Whisper");
		this.you = you.getUniqueId();
		this.them = them.getUniqueId();
		
		this.yourName = yourName;
		this.theirName = theirName;
	}
	
	@Override
	public void processChatEvent(Player sender, String message) {
		
		String yourName = "";
		String theirName = "";
		Player reciever = null;
		
		if (sender.getUniqueId().equals(you)){
			yourName = this.yourName;
			theirName = this.theirName;
			reciever = Bukkit.getPlayer(them);
		} else {
			yourName = this.theirName;
			theirName = this.yourName;
			reciever = Bukkit.getPlayer(you);
		}
	
		sender.sendMessage(Txt.parse("[<l>%s<r> to <italic><pink>%s<r>] %s", getTag(), theirName, message));
		reciever.sendMessage(Txt.parse("[<l>%s<r> from <italic><pink>%s<r>] %s", getTag(), yourName, message));
}
	
	@Override
	public boolean canJoin(Player player){
		return (contains(player));
	}
	
	@Override
	public String getDisplayName(Player player){
		for (UUID uuid : getJoinedPlayers()){
			if (player.getUniqueId().equals(uuid)) continue;
			
			Player reciever = Bukkit.getPlayer(uuid);
			String theirName = ChatUtils.getName(reciever, this);
			
			return theirName;
		}
		return "";
	}
	
	public List<String> getJoinedPlayerNames(){
		return (List<String>) Lists.newArrayList(yourName, theirName);
	}

}