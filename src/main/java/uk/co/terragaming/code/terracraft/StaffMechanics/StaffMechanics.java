package uk.co.terragaming.code.terracraft.StaffMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.CommandMechanics;
import uk.co.terragaming.code.terracraft.StaffMechanics.commands.TestCommandListener;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class StaffMechanics implements Mechanic{

	public StaffMechanics(){
		TerraLogger.info("  StaffMechanics Initialized");
	}
	
	public void PreInitialize() {
		// TODO Auto-generated method stub
		
	}

	public void Initialize() {
		CommandMechanics.createCommandManager("staff", "s");
		
		for (int i=1; i < 11; i++){
			CommandMechanics.getCommandManager("staff").registerCommand("test" + i, new TestCommandListener(), "Test " + i, "/test" + i + " [test]");
		}
	}

	public void PostInitialize() {
		// TODO Auto-generated method stub
		
	}

	public void Deinitialize() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEnabled() {
		return true;
	}
}