package uk.co.terragaming.code.terracraft.CharacterMechanics;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;

public class LoginListener implements Listener{

	@EventHandler
	public void PlayerLoginEvent(PlayerLoginEvent event){
		final Player player = event.getPlayer();
		TerraCraft.Server().getScheduler().scheduleSyncDelayedTask(TerraCraft.Plugin(), new Runnable() {
			  public void run() {
				  new CharacterInterface(player);
			  }
			}, 1L);
	}
}
