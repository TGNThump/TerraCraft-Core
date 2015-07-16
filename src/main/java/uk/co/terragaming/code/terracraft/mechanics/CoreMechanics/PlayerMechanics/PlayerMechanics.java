package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.listeners.PlayerJoinQuitMessageOverrideListener;

@MechanicParent("CoreMechanics")
public class PlayerMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static PlayerMechanics getInstance() {
		return (PlayerMechanics) TerraCraft.getMechanic("CoreMechanics.PlayerMechanics");
	}
	
	// Mechanic Variables
	
	
	
	// Mechanic Methods
	
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitMessageOverrideListener(), TerraCraft.plugin);
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
