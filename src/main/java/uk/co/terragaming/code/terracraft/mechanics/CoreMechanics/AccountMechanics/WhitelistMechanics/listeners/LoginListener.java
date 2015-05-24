package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerLoginEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountEvents;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class LoginListener implements Listener{

	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event){
		
		if (AccountMechanics.getInstance().getRegistry().hasAccount(event.getUniqueId())) return;
		
		if (TerraCraft.serverMode.equals(ServerMode.LOADING)){
			event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "LOADING"));
			return;
		} else if (TerraCraft.serverMode.equals(ServerMode.SHUTDOWN)){
			event.disallow(Result.KICK_OTHER, Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "SHUTTING DOWN"));
			return;
		} else {
			try{
				AccountEvents.onPreLogin(event.getUniqueId(), event.getAddress(), event.getName());
				event.allow();
			} catch (TerraException e){
				event.disallow(Result.KICK_OTHER, Txt.parse(e.getMessages().get(0)));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event){
		AccountEvents.onLogin(event.getPlayer());
	}
	
}
