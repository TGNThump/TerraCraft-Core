package uk.co.terragaming.code.terracraft.StaffMechanics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class TestCommandListener implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		TerraLogger.debug("Test");
		return true;
	}
}
