package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChatLogger;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class EmoteChannel extends Channel {
	
	public EmoteChannel() {
		setTag("L");
	}
	
	@Override
	public void processChatEvent(Player sender, String message) {
		Language lang = Language.ENGLISH;
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		if (registry.hasAccount(sender)) {
			lang = registry.getAccount(sender).getLanguage();
		}
		
		for (UUID uuid : getMutedPlayers()) {
			if (uuid.equals(sender.getUniqueId())) {
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "ChatChannelMuted")));
				return;
			}
		}
		
		String name = ChatUtils.getName(sender, this);
		
		Integer range = getRange();
		
		if (range == -1) {
			for (UUID uuid : getJoinedPlayers()) {
				Player reciever = Bukkit.getPlayer(uuid);
				reciever.sendMessage(Txt.parse("[<l>%s<r>] <silver>%s %s", getTag(), name, message));
			}
		} else {
			sender.sendMessage(Txt.parse("[<l>%s<r>] <silver>%s %s", getTag(), name, message));
			for (Entity entity : sender.getNearbyEntities(range, 500, range)) {
				if (!(entity instanceof Player)) {
					continue;
				}
				Player reciever = (Player) entity;
				if (contains(reciever.getUniqueId())) {
					reciever.sendMessage(Txt.parse("[<l>%s<r>] <silver>%s %s", getTag(), name, message));
				}
			}
		}
		
		if (registry.hasAccount(sender)) {
			Account account = registry.getAccount(sender);
			Character character = account.getActiveCharacter();
			
			ChatLogger.log(account, character, this, message);
		}
	}
	
}
