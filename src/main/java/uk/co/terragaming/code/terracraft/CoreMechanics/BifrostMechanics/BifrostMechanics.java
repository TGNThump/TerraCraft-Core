package uk.co.terragaming.code.terracraft.CoreMechanics.BifrostMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ServerMode;

public class BifrostMechanics implements Mechanic{

	public boolean isEnabled() 	{ return ServerMode.fromString(TerraCraft.Config().get("TerraCraft.Server.Mode").toString()).equals(ServerMode.BIFROST); }
	public boolean isCore() 	{ return true; }
	
	public static BifrostMechanics getInstance(){
		return (BifrostMechanics) TerraCraft.getMechanic("BifrostMechanics");
	}

	// Mechanic Variables
	
	
	// Mechanic Methods
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		TerraCraft.Server().getPluginManager().registerEvents(new BifrostLoginListener(), TerraCraft.Plugin());
	}

	@Override
	public void Initialize() {
		
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