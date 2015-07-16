package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.events.account.AccountLoginEvent;
import uk.co.terragaming.code.terracraft.events.account.AccountPostLoginEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;


public class LoginEventHandler implements Listener{
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		if (!AccountRegistry.hasAccount(player)) return;
		
		Account account = AccountRegistry.getAccount(player);
		account.setPlayer(player);
		
		AccountLoginEvent e1 = new AccountLoginEvent(account);
		Bukkit.getServer().getPluginManager().callEvent(e1);
		
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				AccountPostLoginEvent e2 = new AccountPostLoginEvent(account);
				Bukkit.getServer().getPluginManager().callEvent(e2);
			}
			
		});
	}
}
