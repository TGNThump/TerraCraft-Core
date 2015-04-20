package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class TestCommand {
	
	@Command({"test", "t"})
	@CommandDescription("Test Commands")
	@CommandUsage("<c>/test help")
	@HelpCommand
	public void onTestCommand(CommandSender sender) throws TerraException{
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Use <c>/test help<r> for a list of commands."));
	}
	
	@Command("args")
	@CommandDescription("Test Arguments")
	@CommandParent("test")
	@CommandUsage("<c>/test args")
	@HelpCommand
	public void onTestArgsCommand(CommandSender sender) throws TerraException{
		TerraLogger.debug("Test Child 1");
	}
	
	@Command({"required", "req"})
	@CommandDescription("Test Required Arguments")
	@CommandParent("test args")
	@CommandUsage("<c>/test args required <p><testString> <testInt>")
	public void onTestArgRequiredCommand(CommandSender sender, String testString, Integer testInt) throws TerraException{
		List<String> lines = Txt.parseWrap(Txt.repeat(testString + "\n", testInt));
		for (String line : lines){
			sender.sendMessage(line);
		}
	}
	
	@Command({"optional", "opt"})
	@CommandDescription("Test Optional Arguments")
	@CommandParent("test args")
	@CommandUsage("<c>/test args optional <p>[testString] [testInt]")
	public void onTestArgOptionalCommand(CommandSender sender, @OptArg("defaultValue") String testString, @OptArg("1") Integer testInt) throws TerraException{
		List<String> lines = Txt.parseWrap(Txt.repeat(testString + "\n", testInt));
		for (String line : lines){
			sender.sendMessage(line);
		}
	}
	
}
