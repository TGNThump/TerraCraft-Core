package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.utils.Txt;

public abstract class ARAbstract<T> implements AR<T> {

	@Override
	public T read(CommandSender sender) throws CommandException{
		return this.read(null, sender);
	}
	
	@Override
	public T read(String arg) throws CommandException{
		return this.read(arg, null);
	}
	
	@Override
	public T read() throws CommandException{
		return this.read(null, null);
	}
	
	@Override
	public boolean isValid(String arg, CommandSender sender){
		try{
			this.read(arg, sender);
			return true;
		} catch (TerraException e){
			return false;
		}
	}
	
	@Override
	public String getTypeName(){
		int prefixLength = "AR".length();
		String name = this.getClass().getSimpleName();
		
		// We don't want the "AR" part
		name = name.substring(prefixLength);
		
		// We split at uppercase letters, because most class names are camel-case.
		final String[] words = name.split("(?=[A-Z])");
		return Txt.implode(words, " ").toLowerCase();
	}
	
	@Override
	public List<String> getTabListFiltered(CommandSender sender, String arg){
		Collection<String> raw = this.getTabList(sender, arg);
		if (raw == null){ return Collections.emptyList(); }
		
		List<String> ret = new ArrayList<String>();
		arg = arg.toLowerCase();
		
		for (String option : raw){
			if (option.toLowerCase().startsWith(arg)){
				ret.add(option);
			}
		}
		
		return ret;
	}
}
