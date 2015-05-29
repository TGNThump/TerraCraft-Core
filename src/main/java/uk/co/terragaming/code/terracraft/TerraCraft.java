package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.ReloadHandler;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TerraCraft extends JavaPlugin {
	
	private static MechanicLoader loader;
	public static TerraCraft plugin;
	public static Server server;
	
	public static ServerMode serverMode = ServerMode.LOADING;
	public static boolean debugMode = true;
	
	@Override
	public void onEnable() {
		
		TerraLogger.blank();
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + getDescription().getVersion() + " ").length()));
		TerraLogger.info(" <l> Launching TerraCraft V" + getDescription().getVersion() + " ");
		TerraLogger.info(" " + TerraLogger.tools.repeat("-", (" Launching TerraCraft V" + getDescription().getVersion() + " ").length()));
		TerraLogger.blank();
		
		plugin = this;
		server = getServer();
		loader = new MechanicLoader();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig();
		
		Lang.load();
		
		try {
			loader.constructMechanics();
			loader.preInitMechanics();
		} catch (Exception e) {
			TerraLogger.error("Server encountered an error while loading... Shutting Down...");
			e.printStackTrace();
			server.shutdown();
		}
		
		TerraCraft.server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				try {
					loader.initMechanics();
					loader.postInitMechanics();
					TerraLogger.blank();
					TerraLogger.info("All Enabled Mechanics have been Loaded.");
					TerraLogger.blank();
					serverMode = ServerMode.fromString(getConfig().get("TerraCraft.Server.Mode").toString());
					if (serverMode.equals(ServerMode.BIFROST)) {
						TerraLogger.info("<l>Server launched in BIFROST Registration Mode!");
					}
				} catch (Exception e) {
					TerraLogger.error("Server encountered an error while loading... Shutting Down...");
					e.printStackTrace();
					server.shutdown();
				}
			}
		}, 1L);
		
		TerraLogger.blank();
	}
	
	@Override
	public void onDisable() {
		serverMode = ServerMode.SHUTDOWN;
		
		ReloadHandler.run();
		
		loader.preDenitializeMechanics();
		loader.denitializeMechanics();
		loader.postDenitializeMechanics();
		
		loader = null;
		plugin = null;
		server = null;
	}
	
	public static String getServerName() {
		return plugin.getConfig().getString("TerraCraft.Server.Name");
	}
	
	public static Mechanic getMechanic(String mechanicPath) {
		return loader.getMechanic(mechanicPath);
	}
}
