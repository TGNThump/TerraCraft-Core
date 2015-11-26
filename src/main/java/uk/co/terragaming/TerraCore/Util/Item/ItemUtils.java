package uk.co.terragaming.TerraCore.Util.Item;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.TerraCore.Util.Assert.Assert;

import com.comphenix.attribute.AttributeStorage;
import com.google.common.base.Charsets;


public class ItemUtils {
	
	public static UUID uuid = computeUUID("TerraCraft");
	
	public static UUID computeUUID(String id) {
		try {
			final byte[] input = ("5jNT9hjG64C431gOmv0l5hqQaEdueX3lnnUW1Rm2" + id).getBytes(Charsets.UTF_8);
			final ByteBuffer output = ByteBuffer.wrap(MessageDigest.getInstance("MD5").digest(input));
			
			return new UUID(output.getLong(), output.getLong());
			
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Current JVM doesn't support MD5.", e);
		}
	}
	
//	public static boolean isItem(ItemStack is){
//		if (is == null) return false;
//		Integer id = getItemId(is);
//		if (id == null) return false;
//		return ItemSystem.has(id);
//	}
	
	public static Integer getItemId(ItemStack is){
		Assert.notNull(is);
		try {
			if (is.getType().equals(Material.AIR)) return null;
			
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
	
//	public static Item getItem(ItemStack is){
//		if (is == null) return null;
//		Integer id = ItemUtils.getItemId(is);
//		if (id == null) return null;
//		if (!ItemSystem.has(id)) return null;
//		
//		return ItemSystem.get(id);
//	}
}
