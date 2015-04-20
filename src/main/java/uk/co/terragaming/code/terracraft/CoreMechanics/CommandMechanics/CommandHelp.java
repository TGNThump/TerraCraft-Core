package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandHelp {

	public void onHelpCommand(CommandSender sender, CommandAbstract command, @OptArg("1") Integer pageNumber){
		List<String> lines = Lists.newArrayList();
		for(CommandAbstract child : command.getParent().getSubCommands()){
			lines.add(Txt.parse(child.getUsage()));
		}
		
		lines = Txt.getPage(lines, pageNumber, "Help for \"/" + command.getParent().getPath() + "\" Command", sender);
		for (String line : lines){
			sender.sendMessage(line);
		}
	}
}
