package uk.co.terragaming.code.terracraft.StaffMechanics.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;

public class StaffDownloadCharacter implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (args.length >= 1){
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Invalid Command Usage.");
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Usage: /staff downloadChar");
		} else {
			CharacterMechanics characterMechanics = CharacterMechanics.getInstance();
			AccountMechanics accountMechanics = AccountMechanics.getInstance();
			
			Player player = (Player) sender;
			Account account = accountMechanics.getRegistry().getAccount(player);
			Character character = characterMechanics.getAccountActiveCharater(account);
			
			character.setActiveCharacter();
			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Successfully Downloaded Character Data from Database.");
		}
		return true;
	}
	
}
