package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.ChatMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;

@MechanicParent("CoreMechanics.PlayerMechanics")
public class ChatMechanics implements Mechanic{

	public boolean isEnabled()	{ return true; }
	
	public static ChatMechanics getInstance(){
		return (ChatMechanics) TerraCraft.getMechanic("CoreMechanics.PlayerMechanics.ChatMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events

	@Override
	public void PreInitialize() {
		TerraCraft.server.getPluginManager().registerEvents(new ChatListener(), TerraCraft.plugin);
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
