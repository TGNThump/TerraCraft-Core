package uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class PlayerMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static PlayerMechanics getInstance(){
		return (PlayerMechanics) TerraCraft.getMechanic("PlayerMechanics");
	}

	// Mechanic Variables
	
	
	// Mechanic Methods
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
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