package uk.co.terragaming.code.terracraft.events.account;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.BanMechanics.AccountBan;

public class AccountBanEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Account account;
	private AccountBan ban;
	private Player player;
	
	public AccountBanEvent(Account account, AccountBan ban, Player player){
		this.account = account;
		this.ban = ban;
		this.player = player;
	}
	
	public Account getAccount(){
		return account;
	}
	
	public AccountBan getBan(){
		return ban;
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
