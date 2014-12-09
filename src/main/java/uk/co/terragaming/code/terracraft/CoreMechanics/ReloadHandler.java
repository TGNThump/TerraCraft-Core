package uk.co.terragaming.code.terracraft.CoreMechanics;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ReloadHandler {
	public static void Run(){
		
		Server server = TerraCraft.Server();
		
		if (server.getOnlinePlayers().size() != 0){
			for (Player player : server.getOnlinePlayers()){
				TerraLogger.info("Kicking " + player.getName());
				
				player.kickPlayer(ChatColor.RED + "TerraCraft " + TerraCraft.getServerName() + " is Restarting...");
			}
		}
	}
}
