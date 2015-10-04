package uk.co.terragaming.code.terracraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import uk.co.terragaming.code.terracraft.TerraCraft;


public class Scheduler {
	
	public static BukkitTask runAsync(Runnable task){
		return Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, task);
	}
	
	public static BukkitTask runTimer(Runnable task, long delay, long period){
		return Bukkit.getScheduler().runTaskTimer(TerraCraft.plugin, task, delay, period);
	}
	
}
