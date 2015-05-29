package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.PermissionMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;

@MechanicParent("CoreMechanics.AccountMechanics")
public class PermissionMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static PermissionMechanics getInstance() {
		return (PermissionMechanics) TerraCraft.getMechanic("CoreMechanics.AccountMechanics.PermissionMechanics");
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
