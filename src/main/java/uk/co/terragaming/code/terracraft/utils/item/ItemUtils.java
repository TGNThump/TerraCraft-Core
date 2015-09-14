package uk.co.terragaming.code.terracraft.utils.item;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.utils.Assert;

import com.comphenix.attribute.AttributeStorage;

public class ItemUtils {
	
	private static ItemSystem ItemSystem = ItemMechanics.getInstance().getItemSystem();
	
	public static boolean isItem(ItemStack is){
		if (is == null) return false;
		Integer id = getItemId(is);
		if (id == null) return false;
		return ItemSystem.has(id);
	}
	
	public static Integer getItemId(ItemStack is){
		Assert.notNull(is);
		try {
			if (is.getType().equals(Material.AIR)) return null;
			
			UUID uuid= ItemMechanics.getInstance().getAttributeUUID();
			AttributeStorage storage = AttributeStorage.newTarget(is, uuid);
			String attrData = storage.getData(null);
			
			if (attrData == null) return null;
			if (!attrData.startsWith("TCID: ")) return null;
			
			Integer id = Integer.parseInt(attrData.substring(6));
			return id;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Item getItem(ItemStack is){
		if (is == null) return null;
		Integer id = ItemUtils.getItemId(is);
		if (id == null) return null;
		if (!ItemSystem.has(id)) return null;
		
		return ItemSystem.get(id);
	}
}
