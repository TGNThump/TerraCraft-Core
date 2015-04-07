package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.Iterator;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEvents implements Listener {
	
	@EventHandler
	public void manipulateDrops(EntityDeathEvent event) {
		List<ItemStack> dropList = event.getDrops();
		Iterator<ItemStack> iter = dropList.iterator();
		ItemStack is;
		ItemMechanics itemMechanics = ItemMechanics.getInstance();
		while (iter.hasNext()) {
			is = iter.next();
			
			ItemInstance itemInstance = itemMechanics.getItemInstance(is);
			if (itemInstance == null) {continue;}
			
			switch (itemInstance.getBinding()){
			case NONE:
				break;
			default:
				iter.remove();
				break;
			}
		}
	}
}
