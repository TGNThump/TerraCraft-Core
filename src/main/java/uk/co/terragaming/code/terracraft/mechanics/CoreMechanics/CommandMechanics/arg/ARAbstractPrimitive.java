package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.utils.text.Txt;

public abstract class ARAbstractPrimitive<T> extends ARAbstractException<T> {
	
	@Override
	public String extractErrorMessage(String arg, CommandSender sender, Exception e) {
		return Txt.parse("[<l>TerraCraft<r>] <b>\"<h>%s<b>\"<b> is not a %s.", arg, getTypeName());
	}
}
