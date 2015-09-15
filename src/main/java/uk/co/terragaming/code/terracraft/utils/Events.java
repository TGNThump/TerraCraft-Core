package uk.co.terragaming.code.terracraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.TerraCraft;


public class Events {
	
	public static void register(Listener listener){
		Bukkit.getPluginManager().registerEvents(listener, TerraCraft.plugin);
	}
	
}
