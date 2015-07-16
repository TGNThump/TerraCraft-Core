package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics;

import java.sql.SQLException;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.eventHandler.WhitelistPermChecker;

import com.j256.ormlite.dao.Dao;

@MechanicParent("CoreMechanics")
@MechanicRequires("CoreMechanics.AccountMechanics")
@MechanicRequires("CoreMechanics.DatabaseMechanics")
public class PermissionMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static PermissionMechanics getInstance() {
		return (PermissionMechanics) TerraCraft.getMechanic("CoreMechanics.PermissionMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private Dao<Group, Integer> groupsDao;
	private Dao<GroupMembers, Integer> groupMembersDao;
	
	// Mechanic Methods
	
	public Dao<Group, Integer> getGroupsDao(){
		return groupsDao;
	}
	
	public Dao<GroupMembers, Integer> getGroupMembersDao(){
		return groupMembersDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		groupsDao = (Dao<Group, Integer>) databaseMechanics.getDao(Group.class);
		groupMembersDao = (Dao<GroupMembers, Integer>) databaseMechanics.getDao(GroupMembers.class);
	
		try {
			groupsDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		new GroupRegistry();
		Bukkit.getPluginManager().registerEvents(new WhitelistPermChecker(), TerraCraft.plugin);
	}
	
	@Override
	public void PostInitialize() {
		
	}
	
	@Override
	public void PreDenitialize() {
		
	}
	
	@Override
	public void Denitialize() {
		
	}
	
	@Override
	public void PostDenitialize() {
		
	}
	
}
