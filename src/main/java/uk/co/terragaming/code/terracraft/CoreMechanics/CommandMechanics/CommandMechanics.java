package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class CommandMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static CommandMechanics getInstance(){
		return (CommandMechanics) TerraCraft.getMechanic("CommandMechanics");
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	@Override
	public void Initialize() {
		JavaPlugin plugin = TerraCraft.Plugin();
		CommandHandler.registerCommands(plugin, new VersionCommand());
		CommandHandler.registerCommands(plugin, new TestCommand());
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
