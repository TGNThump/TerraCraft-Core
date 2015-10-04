package uk.co.terragaming.code.terracraft.utils.blocks;

import java.util.List;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.scheduler.BukkitTask;

import uk.co.terragaming.code.terracraft.utils.Scheduler;

import com.google.common.collect.Lists;

public class ChestUtils {
	private static List<Block> openChests = Lists.newArrayList();
	private static BukkitTask task = null;
	
	public static void openChest(Block block){
		if (openChests.contains(block)) return;
		
		Location loc = block.getLocation();
		PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect("random.chestopen",loc.getX(), loc.getY(), loc.getZ(), 1.0F, 1.0F);
		loc.getWorld().getPlayers().forEach(player -> {
			CraftPlayer p = (CraftPlayer) player;
			PlayerConnection connection = p.getHandle().playerConnection;
			connection.sendPacket(packet);
		});
				
		openChests.add(block);
		if (task == null) task = Scheduler.runTimer(ChestUtils::runTask, 0, 5);
		sendChestPacket(block, true);

	}
	
	public static void closeChest(Block block){
		openChests.remove(block);
		if (openChests.isEmpty()){
			if (task != null) task.cancel();
			task = null;
		}
		sendChestPacket(block, false);
	}
	
	private static void runTask(){
		openChests.forEach(chest -> {
			sendChestPacket(chest, true);
		});
	}
	
	private static void sendChestPacket(Block block, Boolean open){
		BlockPosition bp = new BlockPosition(block.getX(), block.getY(), block.getZ());
        net.minecraft.server.v1_8_R3.Block b = CraftMagicNumbers.getBlock(block);
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(bp, b, 1, (open ? 1 : 0));
		block.getLocation().getWorld().getPlayers().forEach(player -> {
			CraftPlayer p = (CraftPlayer) player;
			PlayerConnection connection = p.getHandle().playerConnection;
			connection.sendPacket(packet);
		});
	}
}
