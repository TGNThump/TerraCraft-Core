package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.BifrostMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.enums.ServerMode;

@MechanicParent("CoreMechanics")
public class BifrostMechanics implements Mechanic{

	public boolean isEnabled()	{ return ServerMode.fromString(TerraCraft.plugin.getConfig().get("TerraCraft.Server.Mode").toString()).equals(ServerMode.BIFROST); }
	
	public static BifrostMechanics getInstance(){
		return (BifrostMechanics) TerraCraft.getMechanic("CoreMechanics.BifrostMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events

	@Override
	public void PreInitialize() {
		TerraCraft.server.getPluginManager().registerEvents(new BifrostLoginListener(), TerraCraft.plugin);
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
