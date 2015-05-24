package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.util.List;

import com.google.common.collect.Lists;

public class CommandChild extends Command{
	private String name;
	private List<String> aliases = Lists.newArrayList();
	private String description;
	private String usage;
	private Command parent;

	public CommandChild(String name, Integer paramsLength){
		super(paramsLength);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPath() {
		if (getParent() == null){ return getName();}
		return getParent().getPath() + " " + getName();
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getUsage() {
		return usage;
	}

	@Override
	public Command getParent() {
		return parent;
	}

	@Override
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setUsage(String usage) {
		this.usage = usage;
	}

	@Override
	public void setParent(Command parent) {
		this.parent = parent;
	}
}
