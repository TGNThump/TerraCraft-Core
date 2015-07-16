package uk.co.terragaming.code.terracraft.events.character;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;

public class CharacterLeaveEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Character character;
	private Player player;
	
	public CharacterLeaveEvent(Character character, Player player){
		this.character = character;
		this.player = player;
	}
	
	public Character getCharacter(){
		return character;
	}
	
	public Player getPlayer(){
		return player;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
