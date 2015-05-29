package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.google.common.collect.Lists;

public class BukkitCommand extends Command {
	
	private TabExecutor exe = null;
	
	protected BukkitCommand(String name) {
		super(name);
	}
	
	public void setTabExecutor(TabExecutor exe) {
		this.exe = exe;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (exe == null)
			return false;
		exe.onCommand(sender, this, label, args);
		return false;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (exe == null)
			return Lists.newArrayList();
		return exe.onTabComplete(sender, this, alias, args);
	}
}
