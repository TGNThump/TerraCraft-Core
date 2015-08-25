package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics;

import java.util.Collection;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ChestContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.WorldContainer;

public class World {

	private String name;
	private org.bukkit.World bWorld;
	
	public WorldContainer getContainer() {
		return ItemSystem.get().getDroppedItems(this);
	}
	
	public Collection<ChestContainer> getChests() {
		return ItemSystem.get().getChests(this);
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
}
