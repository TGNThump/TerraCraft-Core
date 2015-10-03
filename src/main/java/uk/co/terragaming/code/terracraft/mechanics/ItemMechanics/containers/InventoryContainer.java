package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RenderComponent;
import uk.co.terragaming.code.terracraft.utils.text.Txt;


public abstract class InventoryContainer extends Container implements InventoryHolder{

	private Inventory inventory;
	
	public ItemStack[] getContents(){
		ItemStack[] ret = new ItemStack[getSize()];
		
		for (Item i : this)
			ret[i.getSlotId()] = i.as(RenderComponent.class).render();
		
		return ret;
	}
	
	public Inventory getInventory() {
		if (inventory == null){
			inventory = Bukkit.createInventory(this , getSize(), Txt.parse("<white>" + getName() + " [<h>%s<white>]<r>", Integer.toHexString(getId()).toUpperCase()));
			inventory.setContents(getContents());
		}
		return inventory;
	}
	
	public abstract String getName();
	
	@Override
	public void refresh(){
		super.refresh();
		if (inventory != null) inventory.setContents(getContents());
	}
}
