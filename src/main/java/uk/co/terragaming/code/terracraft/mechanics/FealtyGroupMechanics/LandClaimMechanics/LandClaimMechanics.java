package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.LandClaimMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class LandClaimMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static LandClaimMechanics getInstance() {
		return (LandClaimMechanics) TerraCraft.getMechanic("LandClaimMechanics");
	}
	
	// Mechanic Variables
	
	// Mechanic Methods
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
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
