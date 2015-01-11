package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

public class CustomCommand {
	final public String name;
	final public String desc;
	final public String usage;
	
	public CustomCommand(String name, String desc, String usage){
		if (name.isEmpty() || desc.isEmpty() || usage.isEmpty()){
			throw new IllegalArgumentException("Invalid Arguments");
		}
		this.name = name;
		this.desc = desc;
		this.usage = usage;
	}
}