package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import org.bukkit.command.CommandSender;

public class ARInteger extends ARAbstractNumber<Integer>{

	@Override
	public String getTypeName(){
		return "number";
	}
	
	@Override
	public Integer valueOf(String arg, CommandSender sender) throws Exception {
		return Integer.parseInt(arg);
	}

	
}
