package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.Iterator;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEventsUntradable implements Listener {
	
	
	@EventHandler
	public void manipulateDrops(EntityDeathEvent event) {
		List<ItemStack> dropList = event.getDrops();
		Iterator<ItemStack> iter = dropList.iterator();
		ItemStack is;
		while (iter.hasNext()) {
			is = iter.next();
			if (is.hasItemMeta()){
				if (is.getItemMeta().hasLore()){
					for (String s : is.getItemMeta().getLore()){
						if (s.contains("Soulbound") || s.contains("Heirloom")){
							iter.remove();
						}
					}
				}
			}
		}
	}
}
