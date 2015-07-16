package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.BanMechanics;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.BanMechanics.eventHandler.WhitelistBanChecker;

@MechanicParent("CoreMechanics.AccountMechanics")
@MechanicRequires("CoreMechanics.AccountMechanics")
@MechanicRequires("CoreMechanics.DatabaseMechanics")
public class BanMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static BanMechanics getInstance() {
		return (BanMechanics) TerraCraft.getMechanic("CoreMechanics.AccountMechanics.BanMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		Bukkit.getPluginManager().registerEvents(new WhitelistBanChecker(), TerraCraft.plugin);
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
