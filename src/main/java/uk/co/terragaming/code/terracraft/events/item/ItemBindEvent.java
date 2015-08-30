package uk.co.terragaming.code.terracraft.events.item;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.BindingComponent;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class ItemBindEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private Item item;
	private BindingComponent binding;
	
	public ItemBindEvent(Item item, BindingComponent binding){
		this.item = item;
		this.binding = binding;

		TerraLogger.info("%s bound to %s.", item, (binding.getBinding().equals(ItemBinding.ACCOUNT) ? binding.getAccOwner() : binding.getCharOwner()));
	}
	
	public BindingComponent getBinding() {
		return binding;
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
