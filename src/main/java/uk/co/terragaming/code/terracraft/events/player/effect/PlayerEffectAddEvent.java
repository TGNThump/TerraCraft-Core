package uk.co.terragaming.code.terracraft.events.player.effect;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEffectAddEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
