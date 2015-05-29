package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.WhitelistMechanics;

import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.exceptions.WhitelistException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountBan;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class WhitelistChecker {
	
	public static void checkBans(Account account) throws WhitelistException, SQLException {
		for (AccountBan ban : account.getBans()) {
			if (!ban.isActive()) {
				continue;
			}
			switch (ban.getType()) {
				case GLOBAL:
					throw new WhitelistException(Lang.get(account.getLanguage(), "accountBanGlobal"));
				case PERM:
					if (ban.getServiceId() != 3) {
						break;
					}
					throw new WhitelistException(Lang.get(account.getLanguage(), "accountBanPerm"));
				case TEMP:
					if (ban.getServiceId() != 3) {
						break;
					}
					DateTime now = DateTime.now();
					
					if (now.isAfter(ban.getDateExpires())) {
						ban.setActive(false);
						account.getBans().update(ban);
						continue;
					}
					
					Period period = new Period(now, ban.getDateExpires());
					PeriodFormatterBuilder Formater = new PeriodFormatterBuilder().appendPrefix(Lang.get(account.getLanguage(), "accountTempBan")).appendDays().appendSeparator(Lang.get(account.getLanguage(), "accountTempBanDays")).appendHours().appendSeparator(Lang.get(account.getLanguage(), "accountTempBanHours")).appendMinutes().appendSeparator(Lang.get(account.getLanguage(), "accountTempBanMinutes")).appendSeconds().appendPrefix(Lang.get(account.getLanguage(), "accountTempBanSeconds"));
					throw new WhitelistException(Formater.toFormatter().print(period));
				default:
					TerraLogger.error("Invalid Ban Type '" + ban.getType().toString() + "' for " + ban.toString());
					throw new WhitelistException(Lang.get(account.getLanguage(), "internalException"));
			}
		}
	}
	
	public static void checkPerms(Account account) throws WhitelistException {
		switch (TerraCraft.serverMode) {
			case BIFROST:
				throw new WhitelistException(Lang.get(account.getLanguage(), "accountServerMode") + "in BIFROST Registration Mode.");
			case CLOSED_BETA:
				// TODO: TGN Beta Members Only
				break;
			case DEVELOPMENT:
				// TODO: TGN Staff and Developers Only
				break;
			case LOADING:
				throw new WhitelistException(Lang.get(account.getLanguage(), "accountServerMode") + "Loading.");
			case LOCKED:
				// TODO: TGN Staff, Developers and Moderators Only
				break;
			case OPEN:
				// TODO: ALL Bifrost Players
				break;
			case OPEN_BETA:
				// TODO: All Bifrost Player
				break;
			case PUBLIC:
				// TODO: All Players
				break;
			case SHUTDOWN:
				throw new WhitelistException(Lang.get(account.getLanguage(), "accountServerMode") + "Shutting Down.");
		}
	}
}
