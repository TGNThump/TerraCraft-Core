package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TerraCraft extends JavaPlugin{
	
	private static Plugin plugin;
	private static Server server;
	
	public void onEnable(){
		
		plugin = this;
		server = this.getServer();
		
	}
	
	public void onDisable(){
		
	}
	
	public static TerraCraft Plugin(){
		return (TerraCraft) plugin;
	}
	
	public static Server Server(){
		return server;
	}
}
