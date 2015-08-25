package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.Perms;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterStaffCommands {
	
	@Command({ "char", "character", "c" })
	@CommandDescription("Staff Command Set")
	@CommandParent("staff")
	@HelpCommand
	public void onCharCommand(CommandSender sender, Language language) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff char help"));
	}
	
	@Command({ "download", "down", "d" })
	@CommandDescription("Force Character Download")
	@CommandParent("staff char")
	public void onCharDownloadCommand(Player sender) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		Account account = AccountRegistry.getAccount(sender);
		Character character = account.getActiveCharacter();
		
		if (character == null) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>No Active Character."));
			return;
		}
		
		CharacterManager.setActiveCharacter(account, character);
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Successfully download character from database."));
	}
	
	@Command({ "upload", "up", "u" })
	@CommandDescription("Force Character Upload")
	@CommandParent("staff char")
	public void onCharUploadCommand(Player sender) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		Account account = AccountRegistry.getAccount(sender);
		Character character = account.getActiveCharacter();
		
		if (character == null) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>No Active Character."));
			return;
		}
		
		CharacterManager.updateActiveCharacter(account, character, false);
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Successfully uploaded character to database."));
	}
}
