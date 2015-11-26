package uk.co.terragaming.Core.Util.Facades;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import uk.co.terragaming.Core.Plugin;


public class Scheduler {
	
	public static int runSync(Runnable task){
		return Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.plugin, task);
	}
	
	public static BukkitTask runSyncTimer(Runnable task, long delay, long period){
		return Bukkit.getScheduler().runTaskTimer(Plugin.plugin, task, delay, period);
	}
	
	public static BukkitTask runAsync(Runnable task){
		return Bukkit.getScheduler().runTaskAsynchronously(Plugin.plugin, task);
	}
	
	public static BukkitTask runAsyncTimer(Runnable task, long delay, long period){
		return Bukkit.getScheduler().runTaskTimerAsynchronously(Plugin.plugin, task, delay, period);
	}
}
