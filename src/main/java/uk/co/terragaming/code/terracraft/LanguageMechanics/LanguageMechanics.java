package uk.co.terragaming.code.terracraft.LanguageMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.LanguageMechanics.ChatListener;

public class LanguageMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static LanguageMechanics getInstance(){
		return (LanguageMechanics) TerraCraft.getMechanic("LanguageMechanics");
	}

	// Mechanic Variables
	
	
	// Mechanic Methods
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		TerraCraft.Server().getPluginManager().registerEvents(new ChatListener(), TerraCraft.Plugin());
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
		
	}
}