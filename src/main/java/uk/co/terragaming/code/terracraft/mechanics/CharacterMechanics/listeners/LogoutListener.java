package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class LogoutListener implements Listener{

	@EventHandler(priority = EventPriority.LOW)
	public void onLogout(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		UUID uuid = player.getUniqueId();
		if (!registry.hasAccount(uuid)) return;
		
		Account account = registry.getAccount(uuid);
		if (account.getActiveCharacter() == null) return;
		
		try {
			CharacterManager.updateActiveCharacter(player, account.getActiveCharacter());
		} catch (SQLException e) {
			event.getPlayer().kickPlayer(Txt.parse(Lang.get("accountInternalError")));
			e.printStackTrace();
		}
	}
	
}
