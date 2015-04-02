package uk.co.terragaming.code.terracraft;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.ReloadHandler;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.utils.ConsoleColor;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.base.Charsets;

public class TerraCraft extends JavaPlugin{
	
	private static MechanicLoader loader;
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
		loader = new MechanicLoader();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getConfig();
		
		loader.constructMechanics();
		loader.preInitMechanics();
		TerraCraft.Server().getScheduler().scheduleSyncDelayedTask(TerraCraft.Plugin(), new Runnable() {
			  public void run() {
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
	
    public static UUID computeUUID(String id) {
	    try {
		    // TODO: REPLACE PREFIX WITH A RANDOM STRING. See random.org
		    final byte[] input = ("5jNT9hjG64C431gOmv0l5hqQaEdueX3lnnUW1Rm2" + id).getBytes(Charsets.UTF_8);
		    final ByteBuffer output = ByteBuffer.wrap(MessageDigest.getInstance("MD5").digest(input));
		     
		    return new UUID(output.getLong(), output.getLong());
		     
	    } catch (NoSuchAlgorithmException e) {
	    // Definitely in violation of the specs
	    throw new IllegalStateException("Current JVM doesn't support MD5.", e);
	    }
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
