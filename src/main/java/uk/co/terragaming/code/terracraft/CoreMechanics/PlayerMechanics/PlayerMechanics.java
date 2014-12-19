package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class PlayerMechanics implements Mechanic{

	public PlayerMechanics(){
		TerraLogger.info("  PlayerMechanics Initialized");
		TerraCraft.Server().getPluginManager().registerEvents(new LoginListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new PingListener(), TerraCraft.Plugin());
	}
	
	public void PreInitialize() {
		TerraCraft.Server().getPluginManager().registerEvents(new LogoutListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new ChatListener(), TerraCraft.Plugin());
	}

	public void Initialize() {
		PlayerMessages.load();
	}

	public void PostInitialize() {
	}

	public void Deinitialize() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isEnabled() {
		return true;
	}
}