package uk.co.terragaming.code.terracraft.mechanics.StaffMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;

@MechanicRequires("CoreMechanics.CommandMechanics")	
public class StaffMechanics implements Mechanic{

	public boolean isEnabled()	{ return true; }
	
	public static StaffMechanics getInstance(){
		return (StaffMechanics) TerraCraft.getMechanic("StaffMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events

	@Override
	public void PreInitialize() {
		
	}

	@Override
	public void Initialize() {
		CommandRegistry.registerCommands(TerraCraft.plugin, new StaffCommands());
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
