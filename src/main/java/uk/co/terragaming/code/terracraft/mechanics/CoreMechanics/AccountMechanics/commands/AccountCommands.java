package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.commands;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class AccountCommands {
	
	@uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command({ "account", "acc" })
	@CommandDescription("Account Command Set.")
	@HelpCommand
	public void onAccountCommand(Player sender, Language language) {
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "account help"));
	}
	
	@uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command({ "language", "lang" })
	@CommandDescription("Set Language")
	@CommandParent("account")
	public void onLangCommand(Player sender, Command command, Language lang, Language language) {
		AccountMechanics.getInstance().getRegistry().getAccount(sender).setLanguage(language);
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandSetLanguage", false)));
	}
	
}
