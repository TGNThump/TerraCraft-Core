package uk.co.terragaming.code.terracraft.utils.blocks;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;


public class BlockUtils {
	
	 public static void playChestAction(Chest chest, boolean open) {
        Location location = chest.getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
        world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
    }
	 
	 public static Location getLocation(InventoryHolder holder) {
		if (holder instanceof BlockState) return ((BlockState) holder).getLocation();
		if (holder instanceof DoubleChest) return ((DoubleChest) holder).getLocation();
		if (holder instanceof Entity) return ((Entity) holder).getLocation();
		return null;
	}
	 
	public static Class<? extends BlockContainer> getContainerType(Block block){
		switch(block.getType()){
			case CHEST:
			case TRAPPED_CHEST:
				return ChestContainer.class;
			default: return null;
		}
	}
	
}
