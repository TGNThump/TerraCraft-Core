package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.listeners.ChestEvents;
import uk.co.terragaming.code.terracraft.utils.Events;


public class WorldMechanics implements Mechanic{
	
	@Override
	public boolean isEnabled(){ return true; }
	
	public static WorldMechanics getInstance(){
		return (WorldMechanics) TerraCraft.getMechanic("WorldMechanics");
	}
	
	// Mechanic Variables
	
	// Mechanics Methods
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	
	@Override
	public void Initialize() throws TerraException{
		try {
			WorldManager.loadWorlds();
			WorldManager.loadWorldContainers();
			WorldManager.loadChestContainers();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TerraException();
		}
	}
	
	@Override
	public void PostInitialize() {
		Events.register(new ChestEvents());
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
