package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.commands;

import java.util.List;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.Perms;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroup;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroupManager;
import uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.FealtyManager;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

import com.google.common.collect.Lists;

public class FealtyCommands {
	
	@Command({ "fealty", "f" })
	@CommandDescription("Staff Fealty Command Set")
	@CommandParent("staff")
	@HelpCommand
	public void onStaffFealtyCommand(Player sender, Language language) {
		Account account = AccountRegistry.getAccount(sender);
		Character you = account.getActiveCharacter();
		
		FealtyGroup group = FealtyGroupManager.getFealtyGroup(you);
		
		if (group == null){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Your Character <n>%s<r> is not part of a Fealty Group.", you.getName()));
		} else if (group.getPatron().equals(you)){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Your Character <n>%s<r> is the leader of " + (group.getName() == null ? "an unnamed fealty group" : "<fg>" + group.getName() + "<r>") + ".", you.getName()));
		} else {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Your Character <n>%s<r> is part of " + (group.getName() == null ? "an unnamed fealty group lead by <n>" + group.getPatron().getName() + "<r>" : "<fg>" + group.getName() + "<r>") + ".", you.getName()));
		}
		
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff fealty help"));
	}

	@Command({ "swear", "s" })
	@CommandDescription("Swear Fealty to another Character")
	@CommandParent("staff fealty")
	public void onStaffFealtySwearCommand(Player sender, Character target){
		Account account = AccountRegistry.getAccount(sender);
		Character you = account.getActiveCharacter();
		
		FealtyManager.swearFealty(you, target);
	}
	
	@Command({ "break", "b" })
	@CommandDescription("Break Fealty to your Patron")
	@CommandParent("staff fealty")
	public void onStaffFealtySwearCommand(Player sender){
		Account account = AccountRegistry.getAccount(sender);
		Character you = account.getActiveCharacter();
		
		FealtyManager.breakFealty(you, you.getPatron());
	}
	
	@Command({ "test" })
	@CommandDescription("Staff Fealty Test Command Set")
	@CommandParent("staff fealty")
	@HelpCommand
	public void onStaffFealtyTestCommand(Player sender, Language language) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff fealty test help"));
	}

	@Command("vassals")
	@CommandParent("staff fealty test")
	@CommandDescription("Test the fealty inheritance tree")
	public void onTestFealty(Player sender) {
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
	
	@Command({ "getGroup", "group" })
	@CommandParent("staff fealty test")
	@CommandDescription("Get the fealtyGroup of your character.")
	public void onStaffTestFealtyGroup(Player sender){
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
		
		FealtyGroup group = FealtyGroupManager.getFealtyGroup(character);
		
		String groupId = (group == null ? "null" : group.getId().toString());
		
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] <h>%s<r>'s Fealty Group: %s", character.getName(), groupId));
	}
}
