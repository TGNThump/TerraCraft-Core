package uk.co.terragaming.code.terracraft.mechanics.WorldMechanics;

import java.util.Collection;
import java.util.HashMap;


public class WorldRegistry{

	public static Collection<World> worlds(){
		return worlds.values();
	}
	
	private static HashMap<String, World> worlds = new HashMap<>();
	
	public static World get(String name) {
		return worlds.get(name);	
	}

	public static World get(org.bukkit.World bWorld) {
		return get(bWorld.getName());
	}
	
	public static void add(String name, World world){
		worlds.put(name, world);
	}
	
	public static void add(World world){
		add(world.getName(), world);
	}
	
	public static boolean has(World world){
		return worlds.containsKey(world.getName());
	}
}
