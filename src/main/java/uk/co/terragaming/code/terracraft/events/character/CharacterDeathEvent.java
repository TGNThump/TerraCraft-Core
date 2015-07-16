package uk.co.terragaming.code.terracraft.events.character;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CharacterDeathEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Character character;
	
	public CharacterDeathEvent(Character character){
		this.character = character;
	}
	
	public Character getCharacter(){
		return character;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
