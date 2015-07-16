package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;

@MechanicParent("CoreMechanics.PlayerMechanics")
public class InterfaceMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static InterfaceMechanics getInstance() {
		return (InterfaceMechanics) TerraCraft.getMechanic("CoreMechanics.PlayerMechanics.InterfaceMechanics");
	}
	
	// Mechanic Variables
	
	private InterfaceEventListener interfaceHandler;
	
	// Mechanic Methods
	
	public InterfaceEventListener getInterfaceHandler(){
		return interfaceHandler;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		interfaceHandler = new InterfaceEventListener();
		Bukkit.getPluginManager().registerEvents(interfaceHandler, TerraCraft.plugin);
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
