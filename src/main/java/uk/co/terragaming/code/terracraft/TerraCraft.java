package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.ReloadHandler;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TerraCraft extends JavaPlugin{

	private static MechanicLoader loader;
	public static TerraCraft plugin;
	public static Server server;
	
	public static ServerMode serverMode = ServerMode.LOADING;
	public static boolean debugMode = true;
	
	public void onEnable(){
		
		TerraLogger.blank();
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + this.getDescription().getVersion() + " ").length()));
		TerraLogger.info(" <l> Launching TerraCraft V" + this.getDescription().getVersion() + " ");
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + this.getDescription().getVersion() + " ").length()));
		TerraLogger.blank();
		
		plugin = this;
		server = this.getServer();
		loader = new MechanicLoader();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig();
		
		Lang.load();
		
		loader.constructMechanics();
		loader.preInitMechanics();

		TerraCraft.server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				loader.initMechanics();
				loader.postInitMechanics();
				TerraLogger.blank();
				TerraLogger.info("All Enabled Mechanics have been Loaded.");
				TerraLogger.blank();
				serverMode = ServerMode.fromString(getConfig().get("TerraCraft.Server.Mode").toString());
				if (serverMode.equals(ServerMode.BIFROST)){
					TerraLogger.info("<l>Server launched in BIFROST Registration Mode!");
				}
			}
		}, 1L);
		
		TerraLogger.blank();
	}
	
	public void onDisable(){
		serverMode = ServerMode.SHUTDOWN;
		
		ReloadHandler.run();
		
		loader.preDenitializeMechanics();
		loader.denitializeMechanics();
		loader.postDenitializeMechanics();
		
		loader = null;
		plugin = null;
		server = null;
	}
	
	public static String getServerName(){
		return plugin.getConfig().getString("TerraCraft.Server.Name");
	}
	
	public static Mechanic getMechanic(String mechanicPath){
		return loader.getMechanic(mechanicPath);
	}
}
