package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.*;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.CommandArgument;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TestCommand {

	@Command({"test", "t"})
	@CommandDescription("Test Root")
	@CommandUsage("/test")
	public void onTestCommand(CommandSender sender, CommandArgument[] args) throws CommandException{
		TerraLogger.debug("Test Root Command");
	}
	
	@Command({"1"})
	@CommandDescription("Test Child 1")
	@CommandParent("test")
	@CommandUsage("/test 1")
	public void onTest1Command(CommandSender sender, CommandArgument[] args) throws CommandException{
		TerraLogger.debug("Test Child 1");
	}
	
	@Command({"2"})
	@CommandDescription("Test subChild 2")
	@CommandParent("test 1")
	@CommandUsage("/test 1 2")
	public void onTest2Command(CommandSender sender, CommandArgument[] args) throws CommandException{
		TerraLogger.debug("Test subChild 2");
	}
	
}
