package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.ReloadHandler;
import uk.co.terragaming.code.terracraft.utils.ConsoleColor;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TerraCraft extends JavaPlugin{

	private static MechanicLoader loader;
	public static Plugin plugin;
	public static Server server;
	
	public static ServerMode serverMode = ServerMode.LOADING;
	
	public void onEnable(){
		
		TerraLogger.blank();
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + this.getDescription().getVersion() + " ").length()));
		TerraLogger.info(" " + ConsoleColor.CYAN + " Launching TerraCraft V" + this.getDescription().getVersion() + " ");
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + this.getDescription().getVersion() + " ").length()));
		TerraLogger.blank();
	
		plugin = this;
		server = this.getServer();
		loader = new MechanicLoader();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig();
		
		loader.constructMechanics();
		TerraCraft.Server().getScheduler().scheduleSyncDelayedTask(TerraCraft.Plugin(), new Runnable() {
			  public void run() {
				  loader.preInitMechanics();
				  loader.initMechanics();
				  TerraLogger.blank();
				  TerraLogger.info("All Enabled Mechanics have been Loaded");
				  TerraLogger.blank();
				  loader.postInitMechanics();
				  serverMode = ServerMode.fromString(TerraCraft.Config().get("TerraCraft.Server.Mode").toString());
				  if(TerraCraft.getServerMode().equals(ServerMode.BIFROST)){
					  TerraLogger.info(ConsoleColor.CYAN + "Server launched in BIFROST Registration Mode!");
				  }
				  ReloadHandler.Run();
			  }
			}, 1L);
		
		TerraLogger.blank();
	}
	
	public void onDisable(){
		loader.preDenitializeMechanics();
		loader.denitializeMechanics();
		loader.postDenitializeMechanics();
		
		loader = null;
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

	public static Mechanic getMechanic(String mechanicName) {
		return loader.getMechanic(mechanicName);
	}
}
