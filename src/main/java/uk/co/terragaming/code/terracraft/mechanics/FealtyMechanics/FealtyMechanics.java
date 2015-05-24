package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class FealtyMechanics implements Mechanic{

	public boolean isEnabled()	{ return true; }
	
	public static FealtyMechanics getInstance(){
		return (FealtyMechanics) TerraCraft.getMechanic("FealtyMechanics");
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
