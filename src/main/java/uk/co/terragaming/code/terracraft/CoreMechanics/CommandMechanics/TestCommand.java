package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandArg;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.ArgType;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.CommandArgument;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class TestCommand {

	@Command({"help", "?"})
	public void onHelpCommand(CommandSender sender, CommandArgument[] args) throws TerraException{
		Integer onlinePlayers = TerraCraft.Server().getOnlinePlayers().size();
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Welcome to TerraCraft."));
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Online: <h>" + onlinePlayers + "<r> Player" + (onlinePlayers == 1 ? "" : "s")));
	}
	
	@Command({"test", "t"})
	@CommandDescription("Test Commands")
	@CommandUsage("/test help")
	public void onTestCommand(CommandSender sender, CommandArgument[] args) throws TerraException{
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Use <c>/test help<r> for a list of commands."));
	}
	
	@Command("args")
	@CommandDescription("Test Arguments")
	@CommandParent("test")
	@CommandUsage("/test args")
	@CommandArg(ArgType.Integer)
	@CommandArg(ArgType.Boolean)
	public void onTest1Command(CommandSender sender, CommandArgument[] args) throws TerraException{
		TerraLogger.debug("Test Child 1");
	}
	
	@Command("2")
	@CommandDescription("Test subChild 2")
	@CommandParent("test args")
	@CommandUsage("/test args 2")
	public void onTest2Command(CommandSender sender, CommandArgument[] args) throws TerraException{
		TerraLogger.debug("Test subChild 2");
	}
	
}
