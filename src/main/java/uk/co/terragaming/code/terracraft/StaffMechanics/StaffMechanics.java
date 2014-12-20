package uk.co.terragaming.code.terracraft.StaffMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.CommandManager;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.CommandMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.StaffMechanics.commands.StaffSpawnCustomItem;
import uk.co.terragaming.code.terracraft.StaffMechanics.commands.TestCommandListener;
import uk.co.terragaming.code.terracraft.StaffMechanics.items.ItemDragonBreath;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class StaffMechanics implements Mechanic{

	public StaffMechanics(){
		TerraLogger.info("  StaffMechanics Initialized");
	}
	
	public void PreInitialize() {
		// TODO Auto-generated method stub
		
	}

	public void Initialize() {
		
		ItemMechanics.itemManager.registerItem("DragonBreath", new ItemDragonBreath());
		
		CommandManager commandManger = CommandMechanics.createCommandManager("staff", "a");
		commandManger.registerCommand("spawncustomitem", new StaffSpawnCustomItem(), "Allows spawning of custom Items.", "/staff spawnCustomItem <ItemName>");
		commandManger.registerCommand("citem", new StaffSpawnCustomItem());
		
		for (int i=1; i < 11; i++){
			commandManger.registerCommand("test" + i, new TestCommandListener(), "Test " + i, "/test" + i + " [test]");
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