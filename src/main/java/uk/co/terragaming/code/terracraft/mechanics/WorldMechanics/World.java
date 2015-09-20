package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.WorldContainer;

import com.google.common.collect.Lists;

public class World {

	private String name;
	private org.bukkit.World bWorld;
	private List<org.bukkit.entity.Item> itemEntities = Lists.newArrayList();
	
	public WorldContainer getContainer() {
		return ItemSystem.get().getDroppedItems(this);
	}
	
	public Collection<ChestContainer> getChests() {
		return ItemSystem.get().getChests(this);
	}
	
	public ChestContainer getChest(Location loc){
		return getChest(loc.getX(), loc.getY(), loc.getZ());
	}
	
	public ChestContainer getChest(double x, double y, double z){
		return getChest(new Vector(x,y,z));
	}
	
	public ChestContainer getChest(Vector loc){
		return ItemSystem.get().getChest(this, loc);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public org.bukkit.World getBukkitWorld() {
		return bWorld;
	}
	
	public void setBukkitWorld(org.bukkit.World world){
		this.bWorld = world;
	}

	public List<org.bukkit.entity.Item> getItemEntities() {
		return itemEntities;
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName() + " '<h>" + getName() + "<r>'";
//		return getClass().getSimpleName() + "[<h>" + getName() + "<r>]";
	}
}
