package uk.co.terragaming.Core.Util.Language;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import uk.co.terragaming.Core.Plugin;
import uk.co.terragaming.Core.Util.Logger.TerraLogger;
import uk.co.terragaming.Core.Util.Text.Text;

public class Lang {
	
	@Inject Lang(){}
	@Inject TerraLogger logger;
	@Inject Plugin plugin;
	
	public HashMap<Locale, ResourceBundle> bundles = new HashMap<>();
	
	public ResourceBundle getBundle(Locale locale){
		if (bundles.containsKey(locale)) return bundles.get(locale);
		
		File dataDir = plugin.getDataFolder();
		try{
			ResourceBundle resources = YamlResourceBundle.getBundle("lang", locale, dataDir);
			bundles.put(locale, resources);
			return resources;
			
		} catch (MissingResourceException e){
			logger.error("No Lang Bundle fo locale '" + locale + "', defaulting to '" + Locale.getDefault() + "'.");
			return YamlResourceBundle.getBundle("lang", Locale.getDefault(), dataDir);
		}
	}
	
	public String get(Locale lang, String key, boolean parsed, boolean console){
		ResourceBundle bundle = getBundle(lang);;
		
		String ret;
		if (bundle.containsKey(key)){
			ret = bundle.getString(key);
		} else{
			logger.error("No translation for '" + key + "' in locale '" + lang.toString() + "'.");
			ret = bundles.get(Locale.getDefault()).getString(key);
		}
		
		if (parsed) ret = Text.of(console, ret);
		return ret;
	}
	
	
}
