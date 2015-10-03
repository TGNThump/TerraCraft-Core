package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemSystem;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.BlockContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ContainerData;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.WorldContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ContainerFactory;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;


public class WorldManager {
	
	public static void loadWorlds(){
		TerraLogger.blank();
		TerraLogger.info("Loading Worlds");
		for (org.bukkit.World bWorld : Bukkit.getWorlds()){
			World world = new World();
			world.setName(bWorld.getName());
			world.setBukkitWorld(bWorld);
			WorldRegistry.add(world);
			TerraLogger.info("	Loaded %s", world);
		}
	}
	
	public static void loadWorldContainers() throws SQLException{
		TerraLogger.blank();
		TerraLogger.info("Loading WorldContainers");
		
		List<ContainerData> results = ContainerData.dao.queryForEq("type", "WorldContainer");
		
		for (ContainerData cd : results){
			WorldContainer c = (WorldContainer) ContainerFactory.create(cd);
			World world = c.getWorld();
			if (world == null) continue;
			ItemSystem.get().addContainer(c);
			TerraLogger.info("	Loaded WorldContainer for %s", world);
		}
		
		for (World w : WorldRegistry.worlds()){
			if (w.getDropsContainer() != null) continue;
			WorldContainer c = ContainerFactory.create(WorldContainer.class, null);
			c.setWorld(w);
			c.update();
			c.refresh();
			ItemSystem.get().addContainer(c);
			TerraLogger.info("	Created WorldContainer[<h>%s<r>] for %s", c.getContainerId(), w);

		}
		
	}
	
	public static void loadChestContainers() throws SQLException{
		TerraLogger.blank();
		TerraLogger.info("Loading ChestContainers");
		
		List<ContainerData> results = ContainerData.dao.queryForEq("type", "ChestContainer");
		
		TerraLogger.info("Loading <h>%s<r> Chests...", results.size());
		
		for (ContainerData cd : results){
			BlockContainer c = (BlockContainer) ContainerFactory.create(cd);
			World world = c.getWorld();
			if (world == null) continue;
			ItemSystem.get().addContainer(c);
		}
		
		for (World w : WorldRegistry.worlds()){
			TerraLogger.info("	Loaded <h>%s<r> ChestContainers for %s", w.getBlockContainers().size(), w);
		}
	}
	
}
