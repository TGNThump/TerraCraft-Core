package uk.co.terragaming.code.terracraft.mechanics.ChatMechanicsV1;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanicsV1.listeners.ChatEventListener;

public class ChatMechanics implements Mechanic{

	public boolean isEnabled()	{ return false; }
	
	public static ChatMechanics getInstance(){
		return (ChatMechanics) TerraCraft.getMechanic("ChatMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events

	@Override
	public void PreInitialize() {
		Bukkit.getPluginManager().registerEvents(new ChatEventListener(), TerraCraft.plugin);
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
