package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.utils.Txt;

public abstract class ARAbstractPrimitive<T> extends ARAbstractException<T>{
	
	@Override
	public String extractErrorMessage(String arg, CommandSender sender, Exception e){
		return Txt.parse("<b>\"<h>%s\"<b> is not a %s.", arg, this.getTypeName());
	}
}
