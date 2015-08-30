package uk.co.terragaming.code.terracraft.events.item;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;

public class ItemMoveEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private Item item;
	private Container to;
	private Container from;
	private boolean canceled = false;
	
	public ItemMoveEvent(Item item, Container from, Container to){
		this.item = item;
		this.to = to;
		this.from = from;
	}
	
	public Container getTo() {
		return to;
	}

	public Container getFrom() {
		return from;
	}

	public Item getItem() {
		return item;
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
		return canceled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.canceled = cancel;
	}
	
}
