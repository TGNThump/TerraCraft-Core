package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.TagArg;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class TestCommand {
	
	@Command({"test", "t"})
	@CommandDescription("Test Commands")
	@HelpCommand
	public void onTestCommand(CommandSender sender) throws TerraException{
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Use <c>/test help<r> for a list of commands."));
	}
	
	@Command("args")
	@CommandDescription("Test Arguments")
	@CommandParent("test")
	@HelpCommand
	public void onTestArgsCommand(CommandSender sender) throws TerraException{
		TerraLogger.debug("Test Child 1");
	}
	
	@Command({"required", "req"})
	@CommandDescription("Test Required Arguments")
	@CommandParent("test args")
	public void onTestArgRequiredCommand(CommandSender sender, String testString, Integer testInt) throws TerraException{
		List<String> lines = Txt.parseWrap(Txt.repeat(testString + "\n", testInt));
		for (String line : lines){
			sender.sendMessage(line);
		}
	}
	
	@Command({"optional", "opt"})
	@CommandDescription("Test Optional Arguments")
	@CommandParent("test args")
	public void onTestArgOptionalCommand(CommandSender sender, @OptArg("defaultValue") String testString, @OptArg("1") Integer testInt) throws TerraException{
		List<String> lines = Txt.parseWrap(Txt.repeat(testString + "\n", testInt));
		for (String line : lines){
			sender.sendMessage(line);
		}
	}
	
	@Command({"tag"})
	@CommandDescription("Test Tag Arguments")
	@CommandParent("test args")
	public void onTestArgTagCommand(CommandSender sender, @TagArg boolean f, String test) throws TerraException{
		sender.sendMessage("" + f);
	}
	
	@Command("tab")
	@CommandDescription("Test Tab Completion")
	@CommandParent("test args")
	public void onTestArgTabCommand(CommandSender sender, boolean test){
		sender.sendMessage("" + test);
	}
	
}
