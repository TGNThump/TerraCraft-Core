package uk.co.terragaming.code.terracraft.events.character.fealty.landclaim;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LandChallengeEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
