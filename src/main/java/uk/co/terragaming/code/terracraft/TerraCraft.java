package uk.co.terragaming.code.terracraft;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.CoreMechanics;
import uk.co.terragaming.code.terracraft.utils.ConsoleColor;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TerraCraft extends JavaPlugin{
	
	private static Plugin plugin;
	private static Server server;
	
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
