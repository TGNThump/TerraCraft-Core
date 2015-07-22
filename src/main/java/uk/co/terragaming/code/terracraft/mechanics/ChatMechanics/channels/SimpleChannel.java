package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChatLogger;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class SimpleChannel extends Channel {
	
	@Override
	public void processChatEvent(Player sender, String message) {
		Language lang = Language.ENGLISH;
		
		if (AccountRegistry.hasAccount(sender)) {
			lang = AccountRegistry.getAccount(sender).getLanguage();
		}
		
		for (UUID uuid : getMutedPlayers()) {
			if (uuid.equals(sender.getUniqueId())) {
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "ChatChannelMuted")));
				return;
			}
		}
		
		String name = "";
		if (sender != null) {
			name = ChatUtils.getName(sender, this);
		}
		
		Integer range = getRange();
		
		if (range == -1) {
			for (UUID uuid : getJoinedPlayers()) {
				Player reciever = Bukkit.getPlayer(uuid);
				if (PlayerEffects.hasEffect(reciever, PlayerEffect.NOCHAT)) continue;
				reciever.sendMessage(Txt.parse("[<l>%s<r>]%s %s", getTag(), sender == null ? "" : " <" + name + ">", message));
			}
		} else {
			sender.sendMessage(Txt.parse("[<l>%s<r>] <%s> %s", getTag(), name, message));
			boolean heared = false;
			for (Entity entity : getNearybyPlayers(sender.getLocation(), range)) {
				if (!(entity instanceof Player)) {
					continue;
				}
				Player reciever = (Player) entity;
				if (reciever.equals(sender)) {
					continue;
				}
				if (PlayerEffects.hasEffect(reciever, PlayerEffect.NOCHAT)) continue;
				if (contains(reciever.getUniqueId())) {
					heared = true;
					reciever.sendMessage(Txt.parse("[<l>%s<r>] <%s> %s", getTag(), name, message));
				}
			}
			if (!heared) {
				List<String> messages = Txt.parseWrap(Lang.get(lang, "chatLocalOutOfRange"), false);
				for (String msg : messages) {
					sender.sendMessage(msg);
				}
			}
		}
		
		if (AccountRegistry.hasAccount(sender)) {
			Account account = AccountRegistry.getAccount(sender);
			Character character = account.getActiveCharacter();
			
			ChatLogger.log(account, character, this, message);
		}
	}
	
	public List<Player> getNearybyPlayers(Location loc, int distance) {
		int squaredDistance = distance * distance;
		List<Player> list = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getLocation().getWorld().equals(loc.getWorld())){
				if (p.getLocation().distanceSquared(loc) < squaredDistance) {
					list.add(p);
				}
			}
		return list;
	}
	
}
