package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RenderComponent;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.World;
import uk.co.terragaming.code.terracraft.mechanics.WorldMechanics.WorldRegistry;
import uk.co.terragaming.code.terracraft.utils.text.Txt;


public class ChestContainer extends Container implements InventoryHolder{
	
	private Location location;
	private World world;
	
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
	
	public ItemStack[] getContents(){
		ItemStack[] ret = new ItemStack[getSize()];
		
		for (Item i : this){
			ret[i.getSlotId()] = i.as(RenderComponent.class).render();
		}
		
		return ret;
	}

	public Inventory getInventory() {
		Inventory inventory = Bukkit.createInventory(this , getSize(), Txt.parse("<white>Chest [<h>%s<white>]<r>", this.getDao().getId()));
		inventory.setContents(getContents());
		return inventory;
	}
}