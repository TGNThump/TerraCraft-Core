package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.commands;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandHelp {

	public void onHelpCommand(CommandSender sender, Command command, @OptArg("1") Integer pageNumber){
		if (sender instanceof Player) sender.sendMessage("");
		List<String> lines = Lists.newArrayList();
		for(Command child : command.getParent().getChildren()){
			lines.add(Txt.parse(child.getUsage()));
		}
		
		lines = Txt.getPage(lines, pageNumber, "Help for \"/" + command.getParent().getPath() + "\" Command", sender);
		for (String line : lines){
			sender.sendMessage(line);
		}
	}
	
	public static void createHelpCommand(JavaPlugin plugin, Command command){
		try{
			CommandHelp handler = new CommandHelp();
			Method method = CommandHelp.class.getMethod("onHelpCommand", CommandSender.class, Command.class, Integer.class);
			Optional<Command> ret = CommandRegistry.createCommand(
				plugin,
				handler,
				method,
				"help",
				Lists.newArrayList("?"),
				"Shows this help message",
				"<c>/" + command.getPath() + " help <p>[pageNumber]",
				Optional.of(command),
				CommandRegistry.getParams(method)
			);
			CommandRegistry.registerCommand(ret.get(), handler, method);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
