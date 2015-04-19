package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanicsOld;

import java.util.HashMap;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class CommandMechanics2 implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static CommandMechanics2 getInstance(){
		return (CommandMechanics2) TerraCraft.getMechanic("CommandMechanics2");
	}

	// Mechanic Variables
	private HashMap<String, CommandManager> commandManagers = new HashMap<String, CommandManager>();
	
	// Mechanic Methods
	
	public HashMap<String, CommandManager> getCommandManagers() {
		return commandManagers;
	}

	public CommandManager createCommandManager(String commandLabel, String commandAlias){
		CommandManager commandManager = new CommandManager(TerraCraft.Plugin(), commandLabel, commandAlias);
		commandManagers.put(commandLabel, commandManager);
		try{
			TerraCraft.Plugin().getCommand(commandLabel).setExecutor(commandManager);
		} catch (NullPointerException e){
			TerraLogger.error("Could Not Register Command " + commandLabel + " because it was not set in plugin.yml");
		}
		try{
			TerraCraft.Plugin().getCommand(commandAlias).setExecutor(commandManager);
		} catch (NullPointerException e){
			TerraLogger.error("Could Not Register Command " + commandAlias + " because it was not set in plugin.yml");
		}
		return commandManager;
	}
		
	public CommandManager getCommandManager(String commandLabel){
		return commandManagers.get(commandLabel);
	}
	
	public boolean hasCommandManager(String commandLabel){
		return commandManagers.containsKey(commandLabel);
	}
	
	public void removeCommandManager(String commandLabel){
		commandManagers.remove(commandLabel);
	}
	
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
		commandManagers = null;
	}

	@Override
	public void PostDenitialize() {
		
	}
}