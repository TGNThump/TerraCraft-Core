package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.eventHandlers;

import java.util.concurrent.Callable;

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
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.LoadingMode;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;


public class LoginEventHandler implements Listener{
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				LoadingMode.activeFor(player);
				
//				BossBar.createBar(player, "Loading Account Data");
//				BossBar.displayPercentageBar("Loading Account Data", "Account Data Loaded", player, 0, false, false);
//				
				player.getInventory().clear();
//				player.getInventory().setHelmet(new CustomItem(Material.PUMPKIN).getItemStack());
				
				Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, new Runnable(){

					@Override
					public void run() {
						try{
							Account account = AccountRegistry.getAccount(player);
//							BossBar.setPercentage(player, 50);
							account.setPlayer(player);
							AccountLoginEvent e1 = new AccountLoginEvent(account);
							Bukkit.getServer().getPluginManager().callEvent(e1);
							Bukkit.getScheduler().callSyncMethod(TerraCraft.plugin, new Callable<Boolean>(){

								@Override
								public Boolean call() throws Exception {
									AccountPostLoginEvent e2 = new AccountPostLoginEvent(account);
									Bukkit.getServer().getPluginManager().callEvent(e2);
									return true;
								}
								
							});
						} catch (Exception e) {
							e.printStackTrace();
							player.kickPlayer(Txt.parse(Lang.get("internalException")));
							return;
						}
					}
					
				});
			}
			
		});
	}
}
