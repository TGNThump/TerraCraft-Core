package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import java.sql.SQLException;

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
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class LogoutListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onAccountLogout(AccountLogoutEvent event){
		Account account = event.getAccount();
		Player player = account.getPlayer();
		
		Character activeCharacter = account.getActiveCharacter();
		if (activeCharacter == null) return;
		
		CharacterLeaveEvent e1 = new CharacterLeaveEvent(activeCharacter, player);
		Bukkit.getServer().getPluginManager().callEvent(e1);
		
		try{
			CharacterManager.updateActiveCharacter(player, account.getActiveCharacter());
		} catch (SQLException e){
			// TODO: Error Handling
			player.kickPlayer(Txt.parse(Lang.get("accountInternalError")));
			e.printStackTrace();
		}
	}	
}
