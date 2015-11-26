package uk.co.terragaming.TerraCore.Util.Facades;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import uk.co.terragaming.TerraCore.Plugin;


public class Events {
	
	public static void register(Listener listener){
		Bukkit.getPluginManager().registerEvents(listener, Plugin.plugin);
	}
	
	public static void call(Event event){
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
	
}
