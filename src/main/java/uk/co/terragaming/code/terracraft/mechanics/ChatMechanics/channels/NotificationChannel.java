package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChatLogger;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.utils.reflection.ActionBar;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class NotificationChannel extends Channel {
	
	public NotificationChannel() {
		setTag("L");
	}
	
	@Override
	public void processChatEvent(Player sender, String message) {		
		Integer range = getRange();
		
		if (range == -1) {
			for (UUID uuid : getJoinedPlayers()) {
				Player reciever = Bukkit.getPlayer(uuid);
				if (PlayerEffects.hasEffect(reciever, PlayerEffect.NOCHAT)) continue;
				reciever.sendMessage(Txt.parse("[<l>%s<r>] %s", getTag(), message));
				ActionBar.sendMessage(reciever, Txt.parse(message));
			}
		} else {
			sender.sendMessage(Txt.parse("[<l>%s<r>] %s", getTag(), message));
			ActionBar.sendMessage(sender, Txt.parse(message));
			
			for (Entity entity : getNearybyPlayers(sender.getLocation(), range)) {
				if (!(entity instanceof Player)) {
					continue;
				}
				Player reciever = (Player) entity;
				if (PlayerEffects.hasEffect(reciever, PlayerEffect.NOCHAT)) continue;
				if (reciever == sender) continue;
				if (contains(reciever.getUniqueId())) {
					reciever.sendMessage(Txt.parse("[<l>%s<r>] %s", getTag(), message));
					ActionBar.sendMessage(reciever, Txt.parse(message));
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
