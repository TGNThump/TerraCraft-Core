package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.events.character.fealty.fealtygroup.FealtyGroupCreateEvent;
import uk.co.terragaming.code.terracraft.events.character.fealty.fealtygroup.FealtyGroupDestroyEvent;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroup;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;


public class FealtyGroupEventListener implements Listener{
	
	@EventHandler
	public void onFealtyGroupCreate(FealtyGroupCreateEvent event){
		FealtyGroup group = event.getGroup();
		
		TerraLogger.debug("FealtyGroup " + group.getId() + " Created - Owner: " + group.getPatron().getName());
	}
	
	@EventHandler
	public void onFealtyGroupDestroy(FealtyGroupDestroyEvent event){
		FealtyGroup group = event.getGroup();
		
		TerraLogger.debug("FealtyGroup " + group.getId() + " Destroyed - Owner: " + group.getPatron().getName());
	}
}
