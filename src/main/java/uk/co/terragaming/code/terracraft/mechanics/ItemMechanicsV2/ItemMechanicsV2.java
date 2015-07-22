package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2;

import java.util.UUID;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.commands.StaffItemCommands;
import uk.co.terragaming.code.terracraft.utils.AttributeUtil;

public class ItemMechanicsV2 implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return false;
	}
	
	public static ItemMechanicsV2 getInstance() {
		return (ItemMechanicsV2) TerraCraft.getMechanic("ItemMechanicsV2");
	}
	
	// Mechanic Variables
	
	private UUID uuid;
	
	// Mechanic Methods
	
	public UUID getAttributeUUID() {
		return uuid;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		uuid = AttributeUtil.computeUUID("TerraGamingNetwork-TerraCraft");
	}
	
	@Override
	public void Initialize() {
		
	}
	
	@Override
	public void PostInitialize() {
		CommandRegistry.registerCommands(TerraCraft.plugin, new StaffItemCommands());
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
