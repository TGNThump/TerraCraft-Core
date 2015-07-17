package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.events.character.fealty.fealtygroup.FealtyGroupCreateEvent;
import uk.co.terragaming.code.terracraft.events.character.fealty.fealtygroup.FealtyGroupDestroyEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.j256.ormlite.dao.Dao;

public class FealtyGroupManager {

	private static FealtyGroupManager instance;
	private Dao<FealtyGroup, Integer> fealtyGroupsDao;
	
	public void init(){
		instance = this;
		fealtyGroupsDao = FealtyGroupMechanics.getInstance().getFealtyGroupDao();
		
		try {
			for (FealtyGroup group : fealtyGroupsDao.queryForAll()){
				FealtyGroupRegistry.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void destroyFealtyGroup(Character character){
		if (!FealtyGroupRegistry.isGroupPatron(character)) return;
		destroyFealtyGroup(FealtyGroupRegistry.getByPatron(character));
	}
	
	public static void destroyFealtyGroup(FealtyGroup group){
		FealtyGroupDestroyEvent e1 = new FealtyGroupDestroyEvent(group, group.getPatron());
		Bukkit.getPluginManager().callEvent(e1);
		
		FealtyGroupRegistry.remove(group);
		
		try {
			Integer rowsChanged = instance.fealtyGroupsDao.delete(group);
			if (rowsChanged != 1) TerraLogger.error("Failed to Delete Group...: FealtyGroupManager:destroyFealtyGroup(" + group.getId() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static FealtyGroup createFealtyGroup(Character newGroupLeader) {
		if (FealtyGroupRegistry.isGroupPatron(newGroupLeader)) return FealtyGroupRegistry.getByPatron(newGroupLeader);
		FealtyGroup group = new FealtyGroup();
		group.setPatron(newGroupLeader);
		group.setCreateDate(DateTime.now());
		try {
			instance.fealtyGroupsDao.create(group);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		FealtyGroupRegistry.add(group);
		
		FealtyGroupCreateEvent e1 = new FealtyGroupCreateEvent(group, group.getPatron());
		Bukkit.getPluginManager().callEvent(e1);
		
		return group;
	}
	
	public static FealtyGroup getFealtyGroup(Character character){
		if (character.getPatron() == null){
			if (!FealtyGroupRegistry.isGroupPatron(character)) return null;
			return FealtyGroupRegistry.getByPatron(character);
		} else {
			return getFealtyGroup(character.getPatron());
		}
	}
	
}
