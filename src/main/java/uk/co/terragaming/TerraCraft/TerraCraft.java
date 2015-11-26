package uk.co.terragaming.TerraCraft;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import uk.co.terragaming.Core.Plugin;
import uk.co.terragaming.Core.Util.Facades.Reflection;

public class TerraCraft extends Plugin{
	
	@Override
	public void onLoad(){
		super.onLoad();
		removeCommands();
	}

	@Override
	public void onEnable(){
		super.onEnable();	
	}
	
	@Override
	public void onLoaded(){
		super.onLoaded();
	}
	
	@Override
	public void onDisable(){
		super.onDisable();
	}
	
	private void removeCommands() {
		try{
			SimpleCommandMap scm = ((CraftServer) Bukkit.getServer()).getCommandMap();
			Field f = scm.getClass().getDeclaredField("knownCommands");
			Reflection.setAccessible(f);
			Map<?, ?> knownCommands = (Map<?, ?>) f.get(scm);
			
			knownCommands.remove("ver");
			knownCommands.remove("bukkit:version");
			knownCommands.remove("plugins");
			knownCommands.remove("about");
			knownCommands.remove("bukkit:plugins");
			knownCommands.remove("version");
			knownCommands.remove("bukkit:ver");
			knownCommands.remove("bukkit:about");
			knownCommands.remove("bukkit:pl");
			knownCommands.remove("reload");
			knownCommands.remove("bukkit:reload");
			knownCommands.remove("rl");
			knownCommands.remove("bukkit:rl");
			knownCommands.remove("pl");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
