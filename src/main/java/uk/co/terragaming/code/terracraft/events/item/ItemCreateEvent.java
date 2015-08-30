package uk.co.terragaming.code.terracraft.events.item;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ItemCreateEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Item item;
	
	public ItemCreateEvent(Item item){
		this.item = item;
		
		TerraLogger.info("Item[<h>%s<r>] %s Created.", item.getId(), item.getName());
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
	
}
