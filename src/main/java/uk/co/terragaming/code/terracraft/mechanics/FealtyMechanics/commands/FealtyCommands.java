package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.FealtyManager;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class FealtyCommands {

	@Command("testFealty")
	@CommandParent("staff")
	@CommandDescription("Test the fealty inheritance tree")
	public void onTestFealty(Player sender){
		Account account = AccountMechanics.getInstance().getRegistry().getAccount(sender);
		Character character = account.getActiveCharacter();
		
		List<Character> characters = FealtyManager.getVassalsRecursively(character);
		
		List<String> names = Lists.newArrayList();
		
		for (Character c : characters){
			names.add(c.getName());
		}
		
		List<String> lines = Txt.parseWrap(Txt.implodeCommaAndDot(names), true);
		for (String line : lines){
			TerraLogger.debug(line);
		}
	}
}
