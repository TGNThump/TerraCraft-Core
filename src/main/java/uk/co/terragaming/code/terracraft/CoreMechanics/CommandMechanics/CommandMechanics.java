package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.HashMap;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class CommandMechanics implements Mechanic{
	
	private static CommandMechanics instance;
	private static HashMap<String, CommandManager> commandManagers = new HashMap<String, CommandManager>();
	
	public CommandMechanics(){
		TerraLogger.info("  CommandMechanics Initialized");
		instance = this;
	}
	
	public static CommandMechanics getInstance(){
		return instance;
	}
	
	public void PreInitialize() {
		// TODO Auto-generated method stub
		
	}

	public void Initialize() {
		// TODO Auto-generated method stub
		
	}

	public void PostInitialize() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isEnabled() {
		return true;
	}

	public void Deinitialize() {
		commandManagers = null;
	}
	
	public static HashMap<String, CommandManager> getCommandManagers() {
		return commandManagers;
	}

	public static void createCommandManager(String commandLabel, String commandAlias){
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
	}
		
	public static CommandManager getCommandManager(String commandLabel){
		return commandManagers.get(commandLabel);
	}
	
	public static boolean hasCommandManager(String commandLabel){
		return commandManagers.containsKey(commandLabel);
	}
	
	public static void removeCommandManager(String commandLabel){
		commandManagers.remove(commandLabel);
	}

}
