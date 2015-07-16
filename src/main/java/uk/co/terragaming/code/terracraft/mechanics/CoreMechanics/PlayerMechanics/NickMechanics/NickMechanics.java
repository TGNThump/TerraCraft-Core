package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;

@MechanicParent("CoreMechanics.PlayerMechanics")
public class NickMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static NickMechanics getInstance() {
		return (NickMechanics) TerraCraft.getMechanic("CoreMechanics.PlayerMechanics.NickMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		Nicks.init();
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
