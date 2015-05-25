package uk.co.terragaming.code.terracraft.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChatUtils {
	
	public static List<String> getFilteredTabList(Collection<String> raw, String arg){
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
