package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class ItemMechanics implements Mechanic{

	public boolean isEnabled()	{ return true; }
	
	public static ItemMechanics getInstance(){
		return (ItemMechanics) TerraCraft.getMechanic("ItemMechanics");
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