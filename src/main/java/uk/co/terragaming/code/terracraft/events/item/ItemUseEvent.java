package uk.co.terragaming.code.terracraft.events.item;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ItemUseEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Item item;
	
	public ItemUseEvent(Item item){
		this.item = item;
		
		TerraLogger.info("%s Used.", item);
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
