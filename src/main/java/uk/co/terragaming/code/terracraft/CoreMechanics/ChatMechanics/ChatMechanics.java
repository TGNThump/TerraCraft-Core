package uk.co.terragaming.code.terracraft.CoreMechanics.ChatMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.ChatMechanics.ChatListener;

public class ChatMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static ChatMechanics getInstance(){
		return (ChatMechanics) TerraCraft.getMechanic("ChatMechanics");
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