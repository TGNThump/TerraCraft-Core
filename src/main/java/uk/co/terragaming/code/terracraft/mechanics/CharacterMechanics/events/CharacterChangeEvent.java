package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;

public class CharacterChangeEvent extends Event{
	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private Account account;
	private Character character;
	
	public CharacterChangeEvent(Player player, Account account, Character character){
		this.player = player;
		this.account = account;
		this.character = character;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Account getAccount(){
		return account;
	}
	
	public Character getCharacter(){
		return character;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
}
