package uk.co.terragaming.code.terracraft.CoreMechanics.BifrostMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerLoginEvent;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.exceptions.AccountBannedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.exceptions.AccountNotLinkedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics.PlayerMessages;
import uk.co.terragaming.code.terracraft.enums.ServerMode;

public class BifrostLoginListener implements Listener{

	@EventHandler
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event){
		
		if (TerraCraft.getServerMode().equals(ServerMode.LOADING)){
			// Server is still Loading
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "TerraCraft" + TerraCraft.getServerName() + " is still loading...");
			return;
			
		} else {
			Account account = new Account(event.getUniqueId(), event.getAddress());
			AccountMechanics.getInstance().getRegistry().addAccount(account);
			
			try {
				account.downloadData();
				event.disallow(Result.KICK_OTHER, ChatColor.RED + "Your account is allready linked to a Terra Gaming Network Account");
				return;
			} catch (AccountNotLinkedException | AccountBannedException e){
				event.allow();
				return;
			} catch (SQLException e){
				e.printStackTrace();
				event.disallow(Result.KICK_OTHER, PlayerMessages.get("account_sql_exception"));
				return;
			}
		}
	}
	
	@EventHandler
	public void PlayerLoginEvent(PlayerLoginEvent event){
		String userAgent = "Minecraft (" + event.getPlayer().getUniqueId().toString() + ")";
		String hash = event.getHostname().substring(0, event.getHostname().indexOf("."));
		
		try{
			Connection connection = DatabaseMechanics.getInstance().getConnection();
			
			String SQL = "UPDATE accountSessions SET userAgent = ?, enabled = 1, lastUsed = NOW(), hash = NULL WHERE hash = ?";
			
			PreparedStatement query = connection.prepareStatement(SQL);
			query.setString(1, userAgent);
			query.setString(2, hash);
			if(query.execute()){
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "We could not Link your Mojang Account to your Terra Gaming Network Account.");
			} else {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.DARK_AQUA + "Your account has been successfully linked to your Terra Gaming Network Account.");
			}
			connection.close();
			return;
			
			
		} catch (Exception e){
			e.printStackTrace();
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, PlayerMessages.get("account_sql_exception"));
			return;
		}
	}
}
