package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;

public class ARMaterial extends ARAbstractSelect<Material> implements ARAllAble<Material> {
	
	private static ARMaterial i = new ARMaterial();
	
	public static ARMaterial get() {
		return i;
	}
	
	// Override
	
	@Override
	public Material select(String arg, CommandSender sender) throws CommandException {
		
		arg = getComparable(arg);
		
		for (Material mat : Material.values()) {
			String matstr = getComparable(mat);
			if (matstr.equals(arg))
				return mat;
			
			matstr = String.valueOf(mat.ordinal());
			if (matstr.equals(arg))
				return mat;
		}
		
		return null;
	}
	
	@Override
	public Collection<String> altNames(CommandSender sender) {
		List<String> ret = new ArrayList<String>();
		
		for (Material mat : Material.values()) {
			ret.add(String.valueOf(mat.ordinal()));
			ret.add(mat.toString());
		}
		
		return ret;
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> ret = new ArrayList<String>();
		
		for (Material mat : Material.values()) {
			ret.add(getComparable(mat.name()));
		}
		
		return ret;
	}
	
	@Override
	public Collection<Material> getAll(CommandSender sender) {
		return Arrays.asList(Material.values());
	}
	
	// Util
	
	public static String getComparable(Material mat) {
		if (mat == null)
			return null;
		return getComparable(mat.name());
	}
	
	public static String getComparable(String string) {
		if (string == null)
			return null;
		string = string.toLowerCase();
		string = string.replace("_", "");
		string = string.replace(" ", "");
		return string;
	}
	
}
