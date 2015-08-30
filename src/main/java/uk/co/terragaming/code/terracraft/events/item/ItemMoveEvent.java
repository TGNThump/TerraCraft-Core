package uk.co.terragaming.code.terracraft.events.item;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ItemMoveEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private Item item;
	private Container to;
	private Container from;
	private boolean canceled = false;
	
	public ItemMoveEvent(Item item, Container to, Container from){
		this.item = item;
		this.to = to;
		this.from = from;
		
		TerraLogger.info("Item[<h>%s<r>] moved from %s[<h>%s<r>] to %s[<h>%s<r>]", item.getId(), from.getType().getSimpleName(), from.getContainerId(), to.getType().getSimpleName(), to.getContainerId());
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
