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
import uk.co.terragaming.code.terracraft.utils.Callback;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;


public class LoginEventHandler implements Listener{
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				syncLogin(player);
			}
			
		});
	}
	
	private void syncLogin(Player player){
		LoadingMode.activeFor(player);
		
//		BossBarAPI.setMessage(player, "Loading Account Data", 1);
		
		player.getInventory().clear();
//		player.getInventory().setHelmet(new CustomItem(Material.PUMPKIN).getItemStack());
		
		LoginEventHandler instance = this;
		
		Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				asyncLogin(player, Callback.create(instance::loginSuccess, player), Callback.create(instance::loginFailure, player));
			}
			
		});
	}
	
	private void asyncLogin(Player player, Callback<?,?> success, Callback<?,?> failure){
		try{
			Account account = AccountRegistry.getAccount(player);
			account.setPlayer(player);
			
//			BossBarAPI.setHealth(player, 10);
			
			AccountLoginEvent e1 = new AccountLoginEvent(account);
			Bukkit.getServer().getPluginManager().callEvent(e1);
			
//			BossBarAPI.setHealth(player, 50);
			
			Bukkit.getScheduler().callSyncMethod(TerraCraft.plugin, new Callable<Boolean>(){

				@Override
				public Boolean call() throws Exception {
					AccountPostLoginEvent e2 = new AccountPostLoginEvent(account);
					Bukkit.getServer().getPluginManager().callEvent(e2);
					success.call();
					return true;
				}
				
			});
			
		} catch (Exception e){
			e.printStackTrace();
			Bukkit.getScheduler().callSyncMethod(TerraCraft.plugin, new Callable<Boolean>(){

				@Override
				public Boolean call() throws Exception {
					failure.call();
					return true;
				}
				
			});
			
		}
	}
	
	public void loginSuccess(Player player){
//		BossBarAPI.setHealth(player, 100);
//		BossBarAPI.setMessage(player, "Account Loaded", 100, 5);
	}

	public void loginFailure(Player player){
		if (AccountRegistry.hasAccount(player))
			AccountRegistry.removeAccount(AccountRegistry.getAccount(player));
		player.kickPlayer(Txt.parse(Lang.get("internalException")));
	}
}
