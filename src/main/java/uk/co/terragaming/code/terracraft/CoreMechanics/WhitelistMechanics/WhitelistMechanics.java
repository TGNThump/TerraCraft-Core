package uk.co.terragaming.code.terracraft.CoreMechanics.WhitelistMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics.PlayerMessages;

public class WhitelistMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static WhitelistMechanics getInstance(){
		return (WhitelistMechanics) TerraCraft.getMechanic("WhitelistMechanics");
	}
	public WhitelistMechanics(){ Construct();}

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