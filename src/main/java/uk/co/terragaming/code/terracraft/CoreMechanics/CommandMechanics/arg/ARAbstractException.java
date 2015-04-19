package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.utils.Txt;

public abstract class ARAbstractException<T> extends ARAbstract<T> {
	
	public abstract T valueOf(String arg, CommandSender sender) throws Exception;
	
	@Override
	public T read(String arg, CommandSender sender) throws TerraException{
		try{
			return this.valueOf(arg, sender);
		} catch (Exception e){
			throw new TerraException().addMessage(this.extractErrorMessage(arg, sender, e));
		}
	}
	
	public String extractErrorMessage(String arg, CommandSender sender, Exception e){
		return Txt.parse("<b>%s", e.getMessage());

	}
}
