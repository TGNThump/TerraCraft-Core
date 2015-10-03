package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.WorldContainer;

import com.google.common.collect.Lists;

public class World {

	private String name;
	private org.bukkit.World bWorld;
	private List<org.bukkit.entity.Item> itemEntities = Lists.newArrayList();
	
	public WorldContainer getDropsContainer() {
		return ItemSystem.get().getDroppedItems(this);
	}
	
	public Collection<BlockContainer> getBlockContainers() {
		return ItemSystem.get().getBlockContainers(this);
	}
	
	public BlockContainer getBlockContainer(Location loc){
		return getBlockContainer(loc.getX(), loc.getY(), loc.getZ());
	}
	
	public BlockContainer getBlockContainer(double x, double y, double z){
		return getBlockContainer(new Vector(x,y,z));
	}
	
	public BlockContainer getBlockContainer(Vector loc){
		return ItemSystem.get().getBlockContainer(this, loc);
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
	}
}
