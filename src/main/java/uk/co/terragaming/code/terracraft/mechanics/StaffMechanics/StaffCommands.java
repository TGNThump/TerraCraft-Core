package uk.co.terragaming.code.terracraft.mechanics.StaffMechanics;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.Perms;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.VanishEffect;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class StaffCommands {
	
	@Command({ "staff", "admin", "a" })
	@CommandDescription("Staff Command Set")
	@HelpCommand
	public void onStaffCommand(CommandSender sender, Language language) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff help"));
	}
	
	@Command({ "vanish", "v", "disapear" })
	@CommandDescription("Toggle Staff Invisibility")
	@CommandParent("staff")
	public void onVanishCommand(Player sender) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		if (PlayerEffects.hasEffect(sender, PlayerEffect.INVISIBLE)) {
			// Become Visible
			PlayerEffects.removeEffect(sender, PlayerEffect.INVISIBLE);
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You became Visible to other players."));
		} else {
			// Become Invisible
			PlayerEffects.addEffect(sender, PlayerEffect.INVISIBLE);
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You became Invisible to other players."));
		}
	}
	
	@Command({ "seeVanished", "seeInvisible" })
	@CommandDescription("Staff Command Set")
	@CommandParent("staff")
	public void onSeeVanishCommand(Player sender) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		if (VanishEffect.canSeeVanished.contains(sender.getUniqueId())) {
			VanishEffect.canSeeVanished.remove(sender.getUniqueId());
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You can no longer see vanished players."));
		} else {
			// Become Invisible
			VanishEffect.canSeeVanished.add(sender.getUniqueId());
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You can now see vanished players."));
		}
		VanishEffect.vanishRefresh(sender);
	}
}
