package uk.co.terragaming.code.terracraft.StaffMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.CommandHandler;

public class StaffMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return false; }

	// Mechanic Variables
	
	
	// Mechanic Methods
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {

	}

	@Override
	public void Initialize() {
		CommandHandler.registerCommands(TerraCraft.Plugin(), new StaffCommands());
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