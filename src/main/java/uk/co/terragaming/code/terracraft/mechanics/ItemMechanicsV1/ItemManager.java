package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.attribute.AttributeStorage;

public class ItemManager {
	
	public static Integer getItemInstanceId(ItemStack is) {
		if (is.getType().equals(Material.AIR))
			return null;
		UUID uuid = ItemMechanicsV1.getInstance().getAttributeUUID();
		AttributeStorage storage = AttributeStorage.newTarget(is, uuid);
		String attrData = storage.getData(null);
		if (attrData == null)
			return null;
		if (!attrData.startsWith("TCID: "))
			return null;
		Integer id = Integer.parseInt(attrData.substring(6));
		return id;
	}
	
	public static ItemInstance updateItemInstance(ItemInstance item, ItemStack is) {
		
		return item;
	}
	
	public static boolean isItemInstance(ItemStack is) {
		if (is == null)
			return false;
		Integer id = ItemManager.getItemInstanceId(is);
		if (id == null)
			return false;
		ItemInstanceRegistry registry = ItemMechanicsV1.getInstance().getItemInstanceRegistry();
		return registry.hasItem(id);
	}
	
	public static void cleanUpItemsInInventory(Player player) {
		ItemInstanceRegistry registry = ItemMechanicsV1.getInstance().getItemInstanceRegistry();
		for (ItemStack is : player.getInventory()) {
			if (!ItemManager.isItemInstance(is)) {
				continue;
			}
			Integer id = ItemManager.getItemInstanceId(is);
			
			ItemInstance item = registry.getItem(id);
			registry.removeItem(item.getId());
			
			try {
				ItemMechanicsV1.getInstance().getItemInstanceDao().delete(item);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
