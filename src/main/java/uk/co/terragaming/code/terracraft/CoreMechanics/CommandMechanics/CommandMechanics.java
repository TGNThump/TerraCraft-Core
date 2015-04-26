package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Field;

import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class CommandMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static CommandMechanics getInstance(){
		return (CommandMechanics) TerraCraft.getMechanic("CommandMechanics");
	}
	
	private static CommandMap commandMap;
	
	public CommandMap getCommandMap(){
		return commandMap;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		try {
			if (TerraCraft.Server() instanceof CraftServer){
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				f.setAccessible(true);
				commandMap = (CommandMap) f.get(TerraCraft.Server());
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void Initialize() {
		JavaPlugin plugin = TerraCraft.Plugin();
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
