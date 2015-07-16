package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.BanMechanics.eventHandler;

import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.events.account.AccountPreLoginEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.BanMechanics.AccountBan;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;


public class WhitelistBanChecker implements Listener{
	
	@EventHandler
	public void onAccountPreLogin(AccountPreLoginEvent event){
		if (event.isCancelled()) return;
		
		Account account = event.getAccount();
		for (AccountBan ban : account.getBans()){
			if (!ban.isActive()) continue;
			if (checkBan(event, account, ban)) return;
		}
	}
	
	public boolean checkBan(AccountPreLoginEvent event, Account account, AccountBan ban){
		
		switch (ban.getType()){
			case GLOBAL:
				event.setKickMessage(Lang.get(account.getLanguage(), "accountBanGlobal"));
				event.setCancelled(true);
			return true;
				
			case PERM:
				if (ban.getServiceId() != 3) return false;
				event.setKickMessage(Lang.get(account.getLanguage(), "accountBanPerm"));
				event.setCancelled(true);
			return true;
			
			case TEMP:
				if (ban.getServiceId() != 3) return false;
				
				DateTime now = DateTime.now();
				
				if (now.isAfter(ban.getDateExpires())){
					ban.setActive(false);
					try {
						account.getBans().update(ban);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return false;
				}
				
				Language lang = account.getLanguage();
				Period period = new Period(now, ban.getDateExpires());
				PeriodFormatterBuilder formatter = new PeriodFormatterBuilder()
					.appendPrefix(Lang.get(lang, "accountTempBan"))
					.appendDays()
					.appendSeparator(Lang.get(lang, "accountTempBanDays"))
					.appendHours()
					.appendSeparator(Lang.get(lang, "accountTempBanHours"))
					.appendMinutes()
					.appendSeparator(Lang.get(lang, "accountTempBanMinutes"))
					.appendSeconds()
					.appendSuffix(Lang.get(lang, "accountTempBanSeconds"));
				
				event.setKickMessage(formatter.toFormatter().print(period));
				event.setCancelled(true);
			return true;
			
			default:
				TerraLogger.error("Invalid Ban Type '" + ban.getType().toString() + "' for " + ban.toString());
				event.setKickMessage(Lang.get(account.getLanguage(), "internatException"));
				event.setCancelled(true);
			return true;
			
			}
	}
	
}
