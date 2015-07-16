package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.commands;

import java.util.List;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.FealtyManager;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class FealtyCommands {
	
	@Command("testFealty")
	@CommandParent("staff")
	@CommandDescription("Test the fealty inheritance tree")
	public void onTestFealty(Player sender) {
		Account account = AccountRegistry.getAccount(sender);
		Character character = account.getActiveCharacter();
		
		if (character == null) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>No Active Character."));
			return;
		}
		
		List<Character> characters = FealtyManager.getVassalsRecursively(character);
		
		List<String> names = Lists.newArrayList();
		
		for (Character c : characters) {
			names.add(c.getName());
		}
		
		List<String> lines = Txt.parseWrap(Txt.implodeCommaAndDot(names), true);
		for (String line : lines) {
			TerraLogger.debug(line);
		}
	}
}
