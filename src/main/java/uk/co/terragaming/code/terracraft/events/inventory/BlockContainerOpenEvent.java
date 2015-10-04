package uk.co.terragaming.code.terracraft.events.inventory;

import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryView;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;


public class BlockContainerOpenEvent extends InventoryOpenEvent{

	public BlockContainerOpenEvent(InventoryView transaction) {
		super(transaction);
	}
	
	private BlockContainer container;
	
	public BlockContainer getContainer() {
		return container;
	}
	
	public void setContainer(BlockContainer container) {
		this.container = container;
	}
	
}
