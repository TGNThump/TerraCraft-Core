package uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class ItemMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static ItemMechanics getInstance(){
		return (ItemMechanics) TerraCraft.getMechanic("ItemMechanics");
	}

	// Mechanic Variables
	private static ItemManager itemManager;
	
	// Mechanic Methods
	
	public ItemManager getItemManager(){
		return itemManager;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		itemManager = new ItemManager(TerraCraft.Server().getPluginManager(), TerraCraft.Plugin());
		TerraCraft.Server().getPluginManager().registerEvents(new ItemEventsUntradable(), TerraCraft.Plugin());		
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
		
	}

	@Override
	public void PostDenitialize() {
		itemManager = null;
	}
}