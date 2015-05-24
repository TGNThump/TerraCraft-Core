package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;
import uk.co.terragaming.code.terracraft.utils.Txt;

public abstract class ARAbstractException<T> extends ARAbstract<T> {
	
	public abstract T valueOf(String arg, CommandSender sender) throws Exception;
	
	@Override
	public T read(String arg, CommandSender sender) throws CommandException{
		try{
			return this.valueOf(arg, sender);
		} catch (Exception e){
			throw (CommandException) new CommandException().addMessage(this.extractErrorMessage(arg, sender, e));
		}
	}
	
	public String extractErrorMessage(String arg, CommandSender sender, Exception e){
		return Txt.parse("<b>%s", e.getMessage());

	}
}
