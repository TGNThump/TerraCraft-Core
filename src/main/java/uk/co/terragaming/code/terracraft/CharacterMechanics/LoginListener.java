package uk.co.terragaming.code.terracraft.CharacterMechanics;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;

public class LoginListener implements Listener{

	@EventHandler
	public void PlayerLoginEvent(PlayerLoginEvent event){
		Account account = AccountMechanics.getInstance().getRegistry().getAccount(event.getPlayer());
		try {
			CharacterMechanics.getInstance().downloadCharacters(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		final UUID playerUUID = event.getPlayer().getUniqueId();
		TerraCraft.Server().getScheduler().scheduleSyncDelayedTask(TerraCraft.Plugin(), new Runnable() {
			  public void run() {
				  new CharacterInterface(playerUUID);
			  }
			}, 1L);
	}
}
