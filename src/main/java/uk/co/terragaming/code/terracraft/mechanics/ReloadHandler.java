package uk.co.terragaming.code.terracraft.mechanics;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class ReloadHandler {
	
	public static void run() {
		Server server = TerraCraft.server;
		if (server.getOnlinePlayers().size() > 0) {
			
			for (Player player : server.getOnlinePlayers()) {
				TerraLogger.info("Kicking " + player.getName());
				if (AccountRegistry.hasAccount(player)) {
					player.kickPlayer(Txt.parse(Lang.get(AccountRegistry.getAccount(player).getLanguage(), "accountServerMode"), TerraCraft.getServerName(), "Restarting"));
				} else {
					player.kickPlayer(Txt.parse(Lang.get(Language.ENGLISH, "accountServerMode"), TerraCraft.getServerName(), "Restarting"));
				}
			}
		}
	}
}
