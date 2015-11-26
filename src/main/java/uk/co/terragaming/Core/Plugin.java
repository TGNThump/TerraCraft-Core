package uk.co.terragaming.Core;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.Core.Factories.DaggerLoggerFactory;
import uk.co.terragaming.Core.Foundation.MechanicLoader;
import uk.co.terragaming.Core.Util.Facades.Scheduler;
import uk.co.terragaming.Core.Util.Logger.TerraLogger;
import uk.co.terragaming.Core.Util.Text.Text;
import dagger.Module;

@Module
public class Plugin extends JavaPlugin{
	
//	protected HashMap<Class<?>, BaseFactory<?>> factories = new HashMap<Class<?>, BaseFactory<?>>();
//	protected List<Mechanic> mechanics = Lists.newArrayList();
	protected static MechanicLoader loader;
	public static Plugin plugin;
	public static Server server;
	
	public static boolean started = false;
	public static boolean stopped = false;
	
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
			
			started = true;
			onLoaded();
		});
		
		logger.blank();
	}
	
	public void onLoaded(){}
	
	@Override
	public void onDisable(){
		stopped = true;
		
		loader.preDeinitAll();
		loader.deinitAll();
		loader.postDeinitAll();
		
		loader = null;
		plugin = null;
		server = null;
		logger = null;
	}
	
//	public <T> Plugin register(BaseFactory<T> factory){
//		factories.put(factory.make().getClass(), factory);
//		return this;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public <T> T get(Class<T> _class){
//		return (T) factories.get(_class).make();
//	}
	
	public static boolean isStarting(){
		return !started;
	}
	
	public static boolean isStopping(){
		return stopped;
	}
	
	public static boolean isActive(){
		return started && !stopped;
	}
	
}
