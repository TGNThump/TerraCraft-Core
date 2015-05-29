package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;

public class ARBoolean extends ARAbstractPrimitive<Boolean> {
	
	public static final Set<String> TRUE_OPTIONS = new LinkedHashSet<String>(Arrays.asList("y", "ye", "yes", "on", "t", "tr", "tru", "true"));
	
	public static final Set<String> FALSE_OPTIONS = new LinkedHashSet<String>(Arrays.asList("n", "no", "of", "off", "f", "fa", "fal", "fals", "false"));
	
	private static ARBoolean i = new ARBoolean();
	
	public static ARBoolean get() {
		return i;
	}
	
	@Override
	public String getTypeName() {
		return "toggle";
	}
	
	@Override
	public Boolean valueOf(String arg, CommandSender sender) throws Exception {
		arg = arg.toLowerCase();
		
		if (TRUE_OPTIONS.contains(arg))
			return Boolean.TRUE;
		if (FALSE_OPTIONS.contains(arg))
			return Boolean.FALSE;
		
		throw new Exception();
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> ret = new ArrayList<String>();
		
		ret.add("true");
		ret.add("false");
		
		if (!arg.isEmpty()) {
			ret.add("yes");
			ret.add("no");
			ret.add("on");
			ret.add("off");
		}
		
		return ret;
	}
}
