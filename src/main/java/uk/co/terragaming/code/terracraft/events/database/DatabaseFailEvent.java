package uk.co.terragaming.code.terracraft.events.database;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DatabaseFailEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
