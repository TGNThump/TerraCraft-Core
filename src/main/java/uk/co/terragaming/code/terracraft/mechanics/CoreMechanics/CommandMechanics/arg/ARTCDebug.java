package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.enums.TCDebug;
import uk.co.terragaming.code.terracraft.exceptions.CommandException;


public class ARTCDebug extends ARAbstractSelect<TCDebug> implements ARAllAble<TCDebug>{
	
	private static ARTCDebug i = new ARTCDebug();
	
	public static ARTCDebug get(){
		return i;
	}
	
	// Override
	@Override
	public TCDebug select(String arg, CommandSender sender) throws CommandException {
		
		arg = getComparable(arg);
		
		for (TCDebug mat : TCDebug.values()) {
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
		
		for (TCDebug mat : TCDebug.values()) {
			ret.add(String.valueOf(mat.ordinal()));
			ret.add(mat.toString());
		}
		
		return ret;
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> ret = new ArrayList<String>();
		
		for (TCDebug mat : TCDebug.values()) {
			ret.add(getComparable(mat.name()));
		}
		
		return ret;
	}
	

	@Override
	public Collection<TCDebug> getAll(CommandSender sender) {
		return Arrays.asList(TCDebug.values());
	}
	
	// Util
	
		public static String getComparable(TCDebug mat) {
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
