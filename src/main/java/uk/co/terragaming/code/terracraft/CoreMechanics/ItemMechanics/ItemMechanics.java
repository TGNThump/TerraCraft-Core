package uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ItemMechanics implements Mechanic{

	public static ItemManager itemManager;
	
	public ItemMechanics(){
		TerraLogger.info("  ItemMechanics Initialized");
	}
	
	public boolean isEnabled() {
		return true;
	}

	public void PreInitialize() {
		itemManager = new ItemManager(TerraCraft.Server().getPluginManager(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new ItemEventsUntradable(), TerraCraft.Plugin());
	}

	public void Initialize() {

	}

	public void PostInitialize() {
		
	}

	public void Deinitialize() {
		
	}

}
