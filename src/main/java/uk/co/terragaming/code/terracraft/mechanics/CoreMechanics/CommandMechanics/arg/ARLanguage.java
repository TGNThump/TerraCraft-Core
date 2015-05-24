package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.exceptions.CommandException;

public class ARLanguage extends ARAbstractSelect<Language> implements ARAllAble<Language>{

	private static ARLanguage i = new ARLanguage();
	public static ARLanguage get() { return i; }
	
	// Override

	@Override
	public Language select(String arg, CommandSender sender) throws CommandException {
		
		arg = getComparable(arg);
		
		for (Language lang : Language.values()){
			String langstr = getComparable(lang);
			if (langstr.equals(arg)) return lang;
			
			langstr = String.valueOf(lang.ordinal());
			if (langstr.equals(arg)) return lang;
		}
		
		return null;
	}

	@Override
	public Collection<String> altNames(CommandSender sender) {
		List<String> ret = new ArrayList<String>();
		
		for(Language lang : Language.values()){
			ret.add(String.valueOf(lang.ordinal()));
			ret.add(lang.toString());
		}
		
		return ret;
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> ret = new ArrayList<String>();
		
		for (Language lang : Language.values()){
			ret.add(getComparable(lang.name()));
		}
		
		return ret;
	}
	
	@Override
	public Collection<Language> getAll(CommandSender sender)
	{
		return Arrays.asList(Language.values());
	}
	
	// Util
	
	public static String getComparable(Language lang)
	{
		if (lang == null) return null;
		return getComparable(lang.name());
	}
	
	public static String getComparable(String string)
	{
		if (string == null) return null;
		string = string.toLowerCase();
		string = string.replace("_", "");
		string = string.replace(" ", "");
		return string;
	}

}
