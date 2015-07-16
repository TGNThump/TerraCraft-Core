package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChatLogger;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class WhisperChannel extends Channel {
	
	private UUID you;
	private UUID them;
	
	private String yourName;
	private String theirName;
	
	public WhisperChannel(Player you, String yourName, Player them, String theirName) {
		setTag("Whisper");
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
		
		if (sender.getUniqueId().equals(you)) {
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
		
		if (AccountRegistry.hasAccount(sender)) {
			Account account = AccountRegistry.getAccount(sender);
			Character character = account.getActiveCharacter();
			
			ChatLogger.log(account, character, this, message);
		}
		
	}
	
	@Override
	public boolean canJoin(Player player) {
		return contains(player);
	}
	
	@Override
	public String getDisplayName(Player player) {
		if (player.getUniqueId().equals(you))
			return theirName;
		
		return yourName;
	}
	
	public List<String> getJoinedPlayerNames() {
		return Lists.newArrayList(yourName, theirName);
	}
	
}