package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics.listeners.LoginListener;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics.listeners.LogoutListener;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics.listeners.PingListener;

@MechanicParent("CoreMechanics.AccountMechanics")
public class WhitelistMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return !ServerMode.fromString(TerraCraft.plugin.getConfig().get("TerraCraft.Server.Mode").toString()).equals(ServerMode.BIFROST);
	}
	
	public static WhitelistMechanics getInstance() {
		return (WhitelistMechanics) TerraCraft.getMechanic("CoreMechanics.AccountMechanics.WhitelistMechanics");
	}
	
	// Mechanic Variables
	
	// Mechanic Methods
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		TerraCraft.server.getPluginManager().registerEvents(new LoginListener(), TerraCraft.plugin);
		TerraCraft.server.getPluginManager().registerEvents(new LogoutListener(), TerraCraft.plugin);
		TerraCraft.server.getPluginManager().registerEvents(new PingListener(), TerraCraft.plugin);
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
