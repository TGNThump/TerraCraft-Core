package uk.co.terragaming.code.terracraft.utils;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.Material;

import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;

import com.comphenix.attribute.AttributeStorage;

public class ItemUtils {
	
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
}
