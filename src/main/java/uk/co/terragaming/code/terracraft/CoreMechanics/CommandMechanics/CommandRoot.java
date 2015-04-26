package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.List;

import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class CommandRoot extends CommandAbstract{
	
	public final CCommand command;
	
	public CommandRoot(CCommand command){
		this.command = command;
	}

	@Override
	public String getName() {
		return command.getName();
	}

	@Override
	public List<String> getAliases() {
		return command.getAliases();
	}

	@Override
	public String getDescription() {
		return command.getDescription();
	}

	@Override
	public String getUsage() {
		return command.getUsage();
	}

	@Override
	public CommandAbstract getParent() {
		return null;
	}

	@Override
	public void setAliases(List<String> aliases) {
		command.setAliases(aliases);
	}

	@Override
	public void setDescription(String description) {
		command.setDescription(description);
	}

	@Override
	public void setUsage(String usage) {
		command.setUsage(usage);
	}

	@Override
	public void setParent(CommandAbstract parent) {
		TerraLogger.debug("Cannot set parent of CommandBukkit...");
	}

	@Override
	public String getPath() {
		return getName();
	}
}
