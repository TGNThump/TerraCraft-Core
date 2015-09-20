package uk.co.terragaming.code.terracraft.utils;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;


public class BlockUtils {
	
	 public static void playChestAction(Chest chest, boolean open) {
	        Location location = chest.getLocation();
	        World world = ((CraftWorld) location.getWorld()).getHandle();
	        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
	        TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
	        world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
	    }
	
}
