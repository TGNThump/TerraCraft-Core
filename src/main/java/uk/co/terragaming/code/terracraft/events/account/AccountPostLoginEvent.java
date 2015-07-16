package uk.co.terragaming.code.terracraft.events.account;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

public class AccountPostLoginEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Account account;
	
	public AccountPostLoginEvent(Account account) {
		this.account = account;
	}
	
	public Account getAccount(){
		return account;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
