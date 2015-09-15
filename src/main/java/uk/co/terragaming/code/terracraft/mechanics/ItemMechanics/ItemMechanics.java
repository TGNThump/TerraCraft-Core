package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.util.UUID;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.Commands;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.commands.StaffItemCommands;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.listeners.ItemBindNotification;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.listeners.ItemEvents;
import uk.co.terragaming.code.terracraft.utils.AttributeUtil;
import uk.co.terragaming.code.terracraft.utils.Events;


public class ItemMechanics implements Mechanic{
	
	@Override
	public boolean isEnabled(){ return true; }
	
	public static ItemMechanics getInstance(){
		return (ItemMechanics) TerraCraft.getMechanic("ItemMechanics");
	}
	
	// Mechanic Variables
	
	private UUID uuid;
	private ItemSystem itemSystem;
	
	// Mechanics Methods
	
	public UUID getAttributeUUID() {
		return uuid;
	}
	
	public ItemSystem getItemSystem(){
		return itemSystem;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		uuid = AttributeUtil.computeUUID("TerraGamingNetwork-TerraCraft");
		itemSystem = new ItemSystem();
	}
	
	@Override
	public void Initialize(){
		
	}
	
	@Override
	public void PostInitialize() {
		Events.register(new ItemEvents());
		Events.register(new ItemBindNotification());
		Commands.register(new StaffItemCommands());
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
