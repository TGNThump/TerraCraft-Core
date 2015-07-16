package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import uk.co.terragaming.code.terracraft.exceptions.DatabaseException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

public class GroupRegistry {

	private static GroupRegistry instance;
	
	private static Dao<Group, Integer> groupsDao;
	private static Dao<GroupMembers, Integer> groupMembersDao;
	private HashMap<Integer, List<Integer>> groupMembers;
	
	public GroupRegistry(){
		instance = this;
		groupMembers = new HashMap<Integer, List<Integer>>();		// AccountId, List<GroupId>
		groupsDao = PermissionMechanics.getInstance().getGroupsDao();
		groupMembersDao = PermissionMechanics.getInstance().getGroupMembersDao();
	}
	
	public static boolean isInGroup(Account account, Group group){
		Integer accountId = account.getId();
		Integer groupId = group.getId();
		
		if (!instance.groupMembers.containsKey(accountId)) return false;
		return (instance.groupMembers.get(accountId).contains(groupId));
	}
	
	public static List<Group> getGroups(Account account){
		Integer accountId = account.getId();
		if (!instance.groupMembers.containsKey(accountId)) return Lists.newArrayList();
		
		List<Integer> groupIds = instance.groupMembers.get(accountId);
		List<Group> groups = Lists.newArrayList();
		
		for (Integer id : groupIds){
			groups.add(getGroup(id));
		}
		
		return groups;
	}
	
	public static void updateGroups(Account account) throws DatabaseException{
		try {
			Integer accountId = account.getId();
	
			if (instance.groupMembers.containsKey(accountId)){
				instance.groupMembers.remove(accountId);
			}
			
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("accountId", accountId);		
			
			List<GroupMembers> links = groupMembersDao.queryForFieldValues(conditions);
			
			List<Integer> groups = Lists.newArrayList();
			
			for (GroupMembers link : links){
				groups.add(link.getGroup().getId());
			}
			
			instance.groupMembers.put(accountId, groups);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Failed to download account groups...");
		}
	}
	
	public static Group getGroup(Integer id){
		try{
			return groupsDao.queryForId(id);
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
