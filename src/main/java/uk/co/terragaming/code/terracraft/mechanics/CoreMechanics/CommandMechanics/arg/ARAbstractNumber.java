package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class ARAbstractNumber<T extends Number> extends ARAbstractPrimitive<T> {
	
	public static final List<String> TAB_LIST = Collections.singletonList("1");
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		return TAB_LIST;
	}
}
