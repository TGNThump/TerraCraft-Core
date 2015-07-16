package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;

@MechanicParent("CoreMechanics.PlayerMechanics")
public class EffectMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static EffectMechanics getInstance() {
		return (EffectMechanics) TerraCraft.getMechanic("CoreMechanics.PlayerMechanics.EffectMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		PlayerEffects.init();
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
