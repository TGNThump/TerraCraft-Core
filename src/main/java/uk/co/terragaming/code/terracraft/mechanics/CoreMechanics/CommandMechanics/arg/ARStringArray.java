package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.Collection;
import java.util.Collections;

import org.bukkit.command.CommandSender;


public class ARStringArray extends ARAbstract<String[]>{

	private static ARStringArray i = new ARStringArray();
	
	public static ARStringArray get() {
		return i;
	}
	
	@Override
	public String getTypeName() {
		return "text";
	}
	
	@Override
	public String[] read(String arg, CommandSender sender) {
		return arg.split(" ");
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		return Collections.emptySet();
	}
}
