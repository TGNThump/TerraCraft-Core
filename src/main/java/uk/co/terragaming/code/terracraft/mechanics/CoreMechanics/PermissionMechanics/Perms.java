package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics;

import java.util.List;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

import com.google.common.collect.Lists;


public class Perms {
	public static boolean check(Account account, Integer... groupIds){
		List<Group> groups = Lists.newArrayList();
		for (Integer groupId : groupIds){
			groups.add(GroupRegistry.getGroup(groupId));
		}
		return check(account, groups);
	}
	
	public static boolean check(Account account, List<Group> groups){
		for (Group group : groups){
			if (GroupRegistry.isInGroup(account, group)) return true;
		}
		
		return false;
	}
}
