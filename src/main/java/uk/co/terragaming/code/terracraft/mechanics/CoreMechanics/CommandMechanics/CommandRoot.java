package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.util.List;

import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class CommandRoot extends Command {
	
	public final BukkitCommand command;
	
	public CommandRoot(BukkitCommand command, Integer paramsLength) {
		super(paramsLength);
		this.command = command;
	}
	
	@Override
	public String getName() {
		return command.getName();
	}
	
	@Override
	public String getPath() {
		return getName();
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
	public Command getParent() {
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
	public void setParent(Command parent) {
		TerraLogger.error("Cannot set parent of CommandRoot");
		Exception e = new TerraException();
		e.printStackTrace();
	}
}
