package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.commands;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandHelp {
	
	public void onHelpCommand(CommandSender sender, Command command, @OptArg("1") Integer pageNumber) {
		if (sender instanceof Player) {
			sender.sendMessage("");
		}
		List<String> lines = Lists.newArrayList();
		for (Command child : command.getParent().getChildren()) {
			lines.add(Txt.parse(child.getUsage(), !(sender instanceof Player)));
		}
		
		Language lang = Language.ENGLISH;
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (AccountMechanics.getInstance().getRegistry().hasAccount(player)) {
				Account account = AccountMechanics.getInstance().getRegistry().getAccount(player);
				lang = account.getLanguage();
			}
		}
		
		lines = Txt.getPage(lines, pageNumber, Txt.parse(Lang.get(lang, "helpCommandTitle", !(sender instanceof Player)), command.getParent().getPath()), sender);
		for (String line : lines) {
			sender.sendMessage(line);
		}
	}
	
	public static void createHelpCommand(JavaPlugin plugin, Command command) {
		try {
			CommandHelp handler = new CommandHelp();
			Method method = CommandHelp.class.getMethod("onHelpCommand", CommandSender.class, Command.class, Integer.class);
			Optional<Command> ret = CommandRegistry.createCommand(plugin, handler, method, "help", Lists.newArrayList("?"), "Shows this help message", "<c>/" + command.getPath() + " help <p>[pageNumber]", Optional.of(command), CommandRegistry.getParams(method));
			CommandRegistry.registerCommand(ret.get(), handler, method);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
