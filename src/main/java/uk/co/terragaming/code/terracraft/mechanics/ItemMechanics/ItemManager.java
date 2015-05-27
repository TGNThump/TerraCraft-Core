package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import com.comphenix.attribute.AttributeStorage;

public class ItemManager {
	
	public static Integer getItemInstanceId(ItemStack is){
		UUID uuid = ItemMechanics.getInstance().getAttributeUUID();
		AttributeStorage storage = AttributeStorage.newTarget(is, uuid);
		String attrData = storage.getData(null);
		if (attrData == null) return null;
		if (!attrData.startsWith("TCID: ")) return null;
		Integer id = Integer.parseInt(attrData.substring(6));
		return id;
	}
	
	public static ItemInstance updateItemInstance(ItemInstance item, ItemStack is){
//		item.setCurDurability((int) is.getDurability());
		return item;
	}
}
