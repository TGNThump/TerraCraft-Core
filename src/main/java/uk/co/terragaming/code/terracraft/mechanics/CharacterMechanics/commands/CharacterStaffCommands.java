package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.commands;

import java.sql.SQLException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces.CharacterSelectInterface;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterStaffCommands {
	
	@Command({ "char", "character", "c" })
	@CommandDescription("Staff Command Set")
	@CommandParent("staff")
	@HelpCommand
	public void onCharCommand(CommandSender sender, Language language) {
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff char help"));
	}
	
	@Command({ "download", "down", "d" })
	@CommandDescription("Force Character Download")
	@CommandParent("staff char")
	public void onCharDownloadCommand(Player sender) {
		try {
			Account account = AccountRegistry.getAccount(sender);
			Character character = account.getActiveCharacter();
			
			if (character == null) {
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>No Active Character."));
				return;
			}
			
			CharacterManager.setActiveCharacter(account, character);
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Successfully download character from database."));
		} catch (SQLException e) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>Failed to download character from database."));
			e.printStackTrace();
		}
	}
	
	@Command({ "upload", "up", "u" })
	@CommandDescription("Force Character Upload")
	@CommandParent("staff char")
	public void onCharUploadCommand(Player sender) {
		try {
			Account account = AccountRegistry.getAccount(sender);
			Character character = account.getActiveCharacter();
			
			if (character == null) {
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>No Active Character."));
				return;
			}
			
			CharacterManager.updateActiveCharacter(account.getPlayer(), character);
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Successfully uploaded character to database."));
			
		} catch (SQLException e) {
			// TODO: Error Recovery
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>Failed to upload character to database."));
			e.printStackTrace();
		}
	}
	
	@Command({ "switch", "s" })
	@CommandDescription("Switch Character")
	@CommandParent("staff char")
	public void onCharSwitchCommand(Player sender) {
		try {
			Account account = AccountRegistry.getAccount(sender);
			Character character = account.getActiveCharacter();
			
			if (character != null) {
				CharacterManager.updateActiveCharacter(account.getPlayer(), character);
			}
			
		} catch (SQLException e) {
			// TODO: Error Recovery
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>Failed to upload character to database."));
			e.printStackTrace();
		}
		
		new CharacterSelectInterface(sender);
	}
}
