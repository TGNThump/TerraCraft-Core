package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TerraCraft extends JavaPlugin{
	
	private static Plugin plugin;
	private static Server server;
	
	public void onEnable(){
		
		plugin = this;
		server = this.getServer();
		
		getConfig().options().copyDefaults(true);
	    saveConfig();
	    getConfig();
		
	}
	
	public void onDisable(){
		
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
}
