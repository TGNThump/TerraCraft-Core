package uk.co.terragaming.code.terracraft.ItemMechanics;

import java.util.Iterator;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.comphenix.attribute.AttributeStorage;

public class ItemBindEvents implements Listener {

	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent e) {
		ItemStack current = e.getPlayer().getInventory().getItem(e.getNewSlot());

		if (current != null) {
			AttributeStorage storage = AttributeStorage.newTarget(current, TerraCraft.computeUUID("TerraGamingNetwork-TerraCraft"));

			// For retrieving data
			TerraLogger.debug("Attribute data: " + storage.getData(null));
		}
	}

	@EventHandler
	public void manipulateDrops(EntityDeathEvent event) {
		List<ItemStack> dropList = event.getDrops();
		Iterator<ItemStack> iter = dropList.iterator();
		ItemStack is;
		while (iter.hasNext()) {
			is = iter.next();
			if (is.hasItemMeta()) {
				if (is.getItemMeta().hasLore()) {
					for (String s : is.getItemMeta().getLore()) {
						if (s.contains("Soulbound") || s.contains("Heirloom")) {
							iter.remove();
						}
					}
				}
			}
		}
	}
}
