package uk.co.terragaming.code.terracraft.events.account;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

public class AccountPreLoginEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private Account account;
	private boolean cancelled;
	private String kickMessage;
	
	public AccountPreLoginEvent(Account account) {
		this.account = account;
	}
	
	public Account getAccount(){
		return account;
	}
	
	public void setKickMessage(String kickMessage){
		this.kickMessage = kickMessage;
	}
	
	public String getKickMessage(){
		return kickMessage;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
}
