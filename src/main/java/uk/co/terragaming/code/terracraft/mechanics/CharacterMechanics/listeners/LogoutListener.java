package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.events.account.AccountLogoutEvent;
import uk.co.terragaming.code.terracraft.events.character.CharacterLeaveEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

public class LogoutListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onAccountLogout(AccountLogoutEvent event){
		Account account = event.getAccount();
		Player player = account.getPlayer();
		
		Character activeCharacter = account.getActiveCharacter();
		if (activeCharacter == null) return;
		
		CharacterLeaveEvent e1 = new CharacterLeaveEvent(activeCharacter, player);
		Bukkit.getServer().getPluginManager().callEvent(e1);
		
		CharacterManager.updateActiveCharacter(account, account.getActiveCharacter(), false);
	}	
}
