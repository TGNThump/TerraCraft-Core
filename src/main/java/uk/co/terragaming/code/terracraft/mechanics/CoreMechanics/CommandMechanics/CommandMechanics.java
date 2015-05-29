package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.lang.reflect.Field;

import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;

@MechanicParent("CoreMechanics")
public class CommandMechanics implements Mechanic{

	public boolean isEnabled()	{ return true; }
	
	public static CommandMechanics getInstance(){
		return (CommandMechanics) TerraCraft.getMechanic("CoreMechanics.CommandMechanics");
	}
	
	// Mechanic Variables
	
	private static CommandMap commandMap;
	
	// Mechanic Methods
	
	public CommandMap getCommandMap(){
		return commandMap;
	}
	
	// Mechanic Events

	@Override
	public void PreInitialize() {
		try {
			if (TerraCraft.server instanceof CraftServer){
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				f.setAccessible(true);
				commandMap = (CommandMap) f.get(TerraCraft.server);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Initialize() {
		if (TerraCraft.debugMode){
			CommandRegistry.registerCommands(TerraCraft.plugin, new TestCommand());
		}
	}

	@Override
	public void PostInitialize() {
		
	}

	@Override
	public void PreDenitialize() {
		
	}

	@Override
	public void Denitialize() {
		commandMap = null;
	}

	@Override
	public void PostDenitialize() {
		
	}

}
