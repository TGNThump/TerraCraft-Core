package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.eventHandler;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.events.account.AccountPreLoginEvent;
import uk.co.terragaming.code.terracraft.exceptions.DatabaseException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.Group;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.GroupRegistry;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

import com.google.common.collect.Lists;


public class WhitelistPermChecker implements Listener{
	
	@EventHandler
	public void onAccountPreLogin(AccountPreLoginEvent event){
		if (event.isCancelled()) return;
		
		Account account = event.getAccount();
		
		try {
			GroupRegistry.updateGroups(account);
		} catch (DatabaseException e) {
			event.setKickMessage(Lang.get("internalException"));
			event.setCancelled(true);
			e.printStackTrace();
			return;
		}
		
		// TODO: Language for Enums...
		
		switch(TerraCraft.serverMode){
			case CLOSED_BETA:
				// Staff, Devs, TCBeta
				if (checkPerms(account, 1, 2, 7)) return;
				event.setKickMessage(Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "in 'CLOSED BETA'"));
				event.setCancelled(true);
				return;
			case DEVELOPMENT:
				// Staff, Devs
				if (checkPerms(account, 1, 2)) return;
				event.setKickMessage(Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "in 'DEVELOPMENT MODE'"));
				event.setCancelled(true);
				return;
			case LOCKED:
				// Staff, Devs, Mods
				if (checkPerms(account, 1, 2, 3)) return;
				event.setKickMessage(Txt.parse(Lang.get("accountServerMode"), TerraCraft.getServerName(), "in 'LOCKED'"));
				event.setCancelled(true);
				return;
			case OPEN:
				// Members
				if (checkPerms(account, 5)) return;
				event.setKickMessage(Lang.get("accountNotLinked"));
				event.setCancelled(true);
				return;
			case OPEN_BETA:
				// Members
				if (checkPerms(account, 5)) return;
				event.setKickMessage(Lang.get("accountNotLinked"));
				event.setCancelled(true);
				return;
			case PUBLIC: return;
			default:
				TerraLogger.error("Unknown Server Mode '%s' in whitelistPermChecker...", TerraCraft.serverMode.toString());
				event.setKickMessage(Lang.get("internalException"));
				event.setCancelled(true);
				break;
		}
		
	}
	
	private boolean checkPerms(Account account, Integer... groupIds){
		List<Group> groups = Lists.newArrayList();
		for (Integer groupId : groupIds){
			groups.add(GroupRegistry.getGroup(groupId));
		}
		return checkPerms(account, groups);
	}
	
	private boolean checkPerms(Account account, List<Group> groups){
		for (Group group : groups){
			if (GroupRegistry.isInGroup(account, group)) return true;
		}
		
		return false;
	}
	
}
