package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;

public class BlockContainer extends InventoryContainer{
	
	private Location location;
	private World world;
	
	public BlockContainer(){
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(){
		data.put("world", world.getName());
		data.put("x", location.getBlockX());
		data.put("y", location.getBlockY());
		data.put("z", location.getBlockZ());
		super.update();
	}
	
	@Override
	public void refresh(){
		super.refresh();
		world = WorldRegistry.get((String) data.get("world"));
		if (world == null){
			location = null;
			return;
		}
		location = new Location(
			world.getBukkitWorld(),
			((Number) data.get("x")).intValue(),
			((Number) data.get("y")).intValue(),
			((Number) data.get("z")).intValue()
		);
	}
	
	public Vector getBlockLocation(){
		return new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public Block getBlock(){
		return getWorld().getBukkitWorld().getBlockAt(getLocation());
	}

	@Override
	public String getName() {
		return "Block";
	}
}