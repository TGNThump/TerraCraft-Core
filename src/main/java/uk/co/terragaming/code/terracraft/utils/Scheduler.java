package uk.co.terragaming.code.terracraft.utils;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.TerraCraft;


public class Scheduler {
	
	public static void runAsync(Runnable task){
		Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, task);
	}
	
}
