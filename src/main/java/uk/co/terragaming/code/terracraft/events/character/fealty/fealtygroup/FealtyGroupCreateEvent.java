package uk.co.terragaming.code.terracraft.events.character.fealty.fealtygroup;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroup;

public class FealtyGroupCreateEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private FealtyGroup group;
	private Character patron;
	
	public FealtyGroupCreateEvent(FealtyGroup group, Character patron){
		this.group = group;
		this.patron = patron;
	}
	
	public Character getPatron(){
		return patron;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public FealtyGroup getGroup() {
		return group;
	}

	public void setGroup(FealtyGroup group) {
		this.group = group;
	}
	
}
