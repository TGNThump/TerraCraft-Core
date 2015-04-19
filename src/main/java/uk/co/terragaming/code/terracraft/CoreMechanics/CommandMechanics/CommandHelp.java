package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CommandHelp {

	public void onHelpCommand(CommandSender sender, CommandAbstract command, @OptArg("1") Integer pageNumber){
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Test Command."));
	}
}
