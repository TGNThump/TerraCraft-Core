package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class PlayerMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static PlayerMechanics getInstance(){
		return (PlayerMechanics) TerraCraft.getMechanic("PlayerMechanics");
	}
	public PlayerMechanics(){ Construct();}

	// Mechanic Variables
	
	
	// Mechanic Methods
	private void Construct(){
		TerraCraft.Server().getPluginManager().registerEvents(new LoginListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new PingListener(), TerraCraft.Plugin());
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		TerraCraft.Server().getPluginManager().registerEvents(new LogoutListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new ChatListener(), TerraCraft.Plugin());
	}

	@Override
	public void Initialize() {
		PlayerMessages.load();
	}

	@Override
	public void PostInitialize() {
		
	}

	@Override
	public void PreDenitialize() {
		
	}

	@Override
	public void Denitialize() {
		
	}

	@Override
	public void PostDenitialize() {
		
	}
}