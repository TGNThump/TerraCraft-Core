package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class TestCommand {
	
	@Command({"test", "t"})
	@CommandDescription("Test Commands")
	@CommandUsage("/test help")
	@HelpCommand
	public void onTestCommand(CommandSender sender) throws TerraException{
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Use <c>/test help<r> for a list of commands."));
	}
	
	@Command("args")
	@CommandDescription("Test Arguments")
	@CommandParent("test")
	@CommandUsage("/test args")
	public void onTest1Command(CommandSender sender) throws TerraException{
		TerraLogger.debug("Test Child 1");
	}
	
	@Command("2")
	@CommandDescription("Test subChild 2")
	@CommandParent("test args")
	@CommandUsage("/test args 2")
	public void onTest2Command(CommandSender sender) throws TerraException{
		TerraLogger.debug("Test subChild 2");
	}
	
}
