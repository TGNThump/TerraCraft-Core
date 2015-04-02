package uk.co.terragaming.code.terracraft.CoreMechanics.WhitelistMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ServerMode;

public class WhitelistMechanics implements Mechanic{

	public boolean isEnabled() 	{ return !ServerMode.fromString(TerraCraft.Config().get("TerraCraft.Server.Mode").toString()).equals(ServerMode.BIFROST);}
	public boolean isCore() 	{ return true; }
	
	public static WhitelistMechanics getInstance(){
		return (WhitelistMechanics) TerraCraft.getMechanic("WhitelistMechanics");
	}

	// Mechanic Variables
	
	
	// Mechanic Methods

	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		TerraCraft.Server().getPluginManager().registerEvents(new WhitelistLoginListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new WhitelistPingListener(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new WhitelistLogoutListener(), TerraCraft.Plugin());
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