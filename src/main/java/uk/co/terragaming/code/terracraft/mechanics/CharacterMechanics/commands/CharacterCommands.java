package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.commands;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces.CharacterSelectInterface;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterCommands {

	@Command({"character", "char","c"})
	@CommandDescription("Switch to a diffrent Character.")
	public void onCharCommand(Player sender, Language language){
		
		AccountMechanics accountMechanics = AccountMechanics.getInstance();
		Account account = accountMechanics.getRegistry().getAccount(sender);
		
		try {
			Character character = account.getActiveCharacter();
			
			if (character != null){
				CharacterManager.updateActiveCharacter(account.getPlayer(), character);
			}
			
		} catch (SQLException e) {
			// TODO: Error Recovery
			
			Language lang = Language.ENGLISH;
			
			if (account != null) lang = account.getLanguage();
			
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "accountUploadFailed")));
			e.printStackTrace();
		}
		
		new CharacterSelectInterface(sender);
	}
	
}
