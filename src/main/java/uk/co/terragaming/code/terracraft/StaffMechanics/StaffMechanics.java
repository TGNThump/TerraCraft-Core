package uk.co.terragaming.code.terracraft.StaffMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanicsOld.CommandManager;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanicsOld.CommandMechanics2;
import uk.co.terragaming.code.terracraft.StaffMechanics.commands.StaffDownloadCharacter;
import uk.co.terragaming.code.terracraft.StaffMechanics.commands.StaffSpawnCustomItem;
import uk.co.terragaming.code.terracraft.StaffMechanics.commands.StaffSwitchCharacter;

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
		CommandMechanics2 commandMechanics = CommandMechanics2.getInstance();

		CommandManager commandManger = commandMechanics.createCommandManager("staff", "a");
		commandManger.registerCommand("spawncustomitem", new StaffSpawnCustomItem(), "Allows spawning of custom Items.", "/staff spawnCustomItem <ItemId>");
		commandManger.registerCommand("citem", new StaffSpawnCustomItem());
		commandManger.registerCommand("switchcharacter", new StaffSwitchCharacter(), "Allows switching character.", "/staff switchCharacter");
		commandManger.registerCommand("char", new StaffSwitchCharacter());
		commandManger.registerCommand("downloadcharacter", new StaffDownloadCharacter(), "Allows force downloading character.", "/staff downloadCharacter");
		commandManger.registerCommand("dchar", new StaffDownloadCharacter());
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