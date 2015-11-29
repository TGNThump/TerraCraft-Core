package uk.co.terragaming.TerraCore.Util;

import javax.inject.Singleton;

import org.bukkit.Server;

import uk.co.terragaming.TerraCore.Plugin;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule{
	
	@Provides @Singleton Plugin providePlugin(){
		return Plugin.plugin;
	}
	
	@Provides @Singleton Server provideServer(){
		return Plugin.server;
	}
	
	@Provides @Singleton TerraLogger provideLogger(Plugin plugin){
		return new TerraLogger(plugin);
	}
}
