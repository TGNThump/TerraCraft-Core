package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.commands.FealtyCommands;

public class FealtyMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static FealtyMechanics getInstance() {
		return (FealtyMechanics) TerraCraft.getMechanic("FealtyMechanics");
	}
	
	// Mechanic Variables
	
	// Mechanic Methods
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	
	@Override
	public void Initialize() {
		
	}
	
	@Override
	public void PostInitialize() {
		CommandRegistry.registerCommands(TerraCraft.plugin, new FealtyCommands());
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
