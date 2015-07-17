package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.commands;

import java.sql.SQLException;

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
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics.NickRegistry;
//import uk.co.terragaming.code.terracraft.mechanics.oldItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterCommands {
	
	@Command({ "character", "char", "c" })
	@CommandDescription("Switch to a diffrent Character.")
	public void onCharCommand(Player sender, Language language) {
		
		Account account = AccountRegistry.getAccount(sender);
		
		try {
			Character character = account.getActiveCharacter();
			
			if (character != null) {
				CharacterManager.updateActiveCharacter(account.getPlayer(), character);
			} else {
//				ItemManager.cleanUpItemsInInventory(account.getPlayer());
			}
			
		} catch (SQLException e) {
			// TODO: Error Recovery
			
			Language lang = Language.ENGLISH;
			
			if (account != null) {
				lang = account.getLanguage();
			}
			
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "accountUploadFailed")));
			e.printStackTrace();
		}
		
		new CharacterSelectInterface(sender);
	}
	
	@Command("name")
	@CommandParent("character")
	@CommandDescription("Set the name of your new  character.")
	public void onCharName(Player sender, String name){
		Account account = AccountRegistry.getAccount(sender);
		if (account == null) return;
		Character character = account.getActiveCharacter();
		if (character == null) return;
		
		if (!character.getName().equals(account.getTerraTag())){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You can only change the name of a character once."));
			return;
		}
		
		character.setName(name);
		
		NickRegistry.setNick(sender.getUniqueId(), name);
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You successfully changed your name to <n>" + name + "<r>."));
	}
	
}
