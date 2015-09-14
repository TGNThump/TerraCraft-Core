package uk.co.terragaming.code.terracraft.mechanics;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.SessionManager;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class ReloadHandler {
	
	public static void run() {
		Server server = TerraCraft.server;
		if (server.getOnlinePlayers().size() > 0) {
			
			for (Player player : server.getOnlinePlayers()) {
				TerraLogger.info("Kicking " + player.getName());
				if (AccountRegistry.hasAccount(player)) {
					try {
						Account account = AccountRegistry.getAccount(player);
						CharacterManager.syncUpdateActiveCharacter(account);
						AccountMechanics.getInstance().getAccountsDao().update(account);
						SessionManager.updateSession(account, false);
						AccountRegistry.removeAccount(account);
						TerraLogger.info("Uploaded Account Data of <n>%s<r>.", account.getTerraTag());
					
						player.kickPlayer(Txt.parse(Lang.get(account.getLanguage(), "accountServerMode"), TerraCraft.getServerName(), "Restarting"));
					} catch (Exception e){
						player.kickPlayer(Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "Restarting"));
						e.printStackTrace();
					}
				} else {
					player.kickPlayer(Txt.parse(Lang.get(Language.ENGLISH, "accountServerMode"), TerraCraft.getServerName(), "Restarting"));
				}
			}
		}
	}
}
