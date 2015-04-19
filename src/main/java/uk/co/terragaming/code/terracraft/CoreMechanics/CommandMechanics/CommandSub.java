package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.List;

import com.google.common.collect.Lists;

public class CommandSub extends CommandAbstract{

	private String name;
	private List<String> aliases = Lists.newArrayList();
	private String desc;
	private String usage;
	private CommandAbstract parent;
	
	public CommandSub(String name){
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getUsage() {
		return usage;
	}

	@Override
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	@Override
	public void setDescription(String description) {
		this.desc = description;
	}

	@Override
	public void setUsage(String usage) {
		this.usage = usage;
	}

	@Override
	public CommandAbstract getParent() {
		return parent;
	}

	@Override
	public void setParent(CommandAbstract parent) {
		this.parent = parent;
	}

	@Override
	public String getPath() {
		if (getParent() == null){ return getName();}
		return getParent().getPath() + " " + getName();
	}

}
