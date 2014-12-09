package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.CoreMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.ServerMode;
import uk.co.terragaming.code.terracraft.utils.ConsoleColor;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TerraCraft extends JavaPlugin{
	
	private static Plugin plugin;
	private static Server server;
	
	public static ServerMode serverMode = ServerMode.LOADING;
	
	public void onEnable(){
		
		TerraLogger.blank();
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + this.getDescription().getVersion() + " ").length()));
		TerraLogger.info(" " + ConsoleColor.CYAN + " Launching TerraCraft V" + this.getDescription().getVersion() + " ");
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + this.getDescription().getVersion() + " ").length()));
		TerraLogger.blank();
	
		plugin = this;
		server = this.getServer();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig();
		
		CoreMechanics.Initialize();
		
		TerraLogger.blank();
	}
	
	public void onDisable(){
		CoreMechanics.Denitialize();
	}
	
	public static void onDatabaseConnectionEstablished(){
		TerraLogger.blank();
		TerraLogger.info("Downloading Data from Terra Gaming CORE...");
		CoreMechanics.DownloadData();
		TerraLogger.info("Download Finished");
		serverMode = ServerMode.fromString(Config().getString("TerraCraft.Server.Mode"));
	}
	
	public static TerraCraft Plugin(){
		return (TerraCraft) plugin;
	}
	
	public static Server Server(){
		return server;
	}
	
	public static FileConfiguration Config(){
		return plugin.getConfig();
	}
	
	public static String getServerName(){
		return Config().getString("TerraCraft.Server.Name");
	}
	
	public static ServerMode getServerMode(){
		return serverMode;
	}
}
