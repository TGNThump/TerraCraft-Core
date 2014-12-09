package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class PlayerMechanics {
	public static void Initialize(){
		PlayerMessages.load();
		TerraCraft.Server().getPluginManager().registerEvents(new LoginListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new LogoutListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new ChatListener(), TerraCraft.Plugin());

		TerraLogger.info("  PlayerMechanics Initialized");
	}
	
	public static void Deinitialize(){
		
	}
}
