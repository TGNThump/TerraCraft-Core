package uk.co.terragaming.code.terracraft.CharacterMechanics;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;

public class LogoutListener implements Listener{

	@EventHandler
	public void onLogout(PlayerQuitEvent event){
		Player player = event.getPlayer();
		Account account = AccountMechanics.getInstance().getRegistry().getAccount(player);
		if(account.getCurCharacterId() == null) return;
		Character character = CharacterMechanics.getInstance().getAccountActiveCharater(account);
		
		character.setLocation(event.getPlayer().getLocation());
		character.setCurExp(Math.round(player.getExp() * 100));
		character.setCurHitpoints((int) Math.round(player.getHealth()));
		character.setCurHunger(player.getFoodLevel());
		character.setCurLevel(player.getLevel());
		
		character.uploadData();
	}
}
