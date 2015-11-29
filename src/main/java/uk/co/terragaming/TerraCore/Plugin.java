package uk.co.terragaming.TerraCore;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.TerraCore.Enums.ServerMode;
import uk.co.terragaming.TerraCore.Factories.DaggerLoggerFactory;
import uk.co.terragaming.TerraCore.Foundation.MechanicLoader;
import uk.co.terragaming.TerraCore.Util.Facades.Scheduler;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCore.Util.Text.Text;
import dagger.Module;

@Module
public class Plugin extends JavaPlugin{
	
	protected static MechanicLoader loader;
	public static Plugin plugin;
	public static Server server;
	
	public static ServerMode serverMode = ServerMode.STARTING;
	
	public static TerraLogger logger;
	
	@Override
	public void onEnable(){
		
		plugin = this;
		server = getServer();
		logger = DaggerLoggerFactory.create().make();	
		loader = new MechanicLoader(logger);
		
		String spacer = Text.repeat("-", (" Launching " + getDescription().getName() + " V" + getDescription().getVersion() + " ").length());
		String msg = " <l> Launching " + getDescription().getName() + " V" + getDescription().getVersion() + " ";
		
		logger.blank();
		logger.info(spacer);
		logger.info(msg);
		logger.info(spacer);
		logger.blank();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig();		
		
		loader.constructAll();
		loader.preInitAll();
		
		Scheduler.runSync(()->{
			loader.initAll();
			loader.postInitAll();
			
			logger.blank();
			logger.info("<l>All <h>%s<l> enabled mechanics have been loaded.<r>", loader.mechanics.size());
			logger.blank();
			
			serverMode = ServerMode.valueOf(getConfig().get("TerraCraft.Server.Mode").toString());
			onLoaded();
		});
		
		logger.blank();
	}
	
	public void onLoaded(){}
	
	@Override
	public void onDisable(){
		serverMode = ServerMode.STOPPING;
		
		loader.preDeinitAll();
		loader.deinitAll();
		loader.postDeinitAll();
		
		loader = null;
		plugin = null;
		server = null;
		logger = null;
	}
	
	public static boolean isStarting(){
		return serverMode.equals(ServerMode.STARTING);
	}
	
	public static boolean isStopping(){
		return serverMode.equals(ServerMode.STOPPING);
	}
	
	public static boolean isActive(){
		return !isStarting() && !isStopping();
	}
	
}
