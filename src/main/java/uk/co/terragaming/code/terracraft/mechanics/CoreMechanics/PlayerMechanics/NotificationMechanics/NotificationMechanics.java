package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics;

import java.sql.SQLException;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.listeners.NotificationListener;

import com.j256.ormlite.dao.Dao;

@MechanicParent("CoreMechanics.PlayerMechanics")
public class NotificationMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static NotificationMechanics getInstance() {
		return (NotificationMechanics) TerraCraft.getMechanic("CoreMechanics.PlayerMechanics.NotificationMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private Dao<Notification, Integer> notificationDao;
	
	// Mechanic Methods
	
	public Dao<Notification, Integer> getNotificationDao() {
		return notificationDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		notificationDao = (Dao<Notification, Integer>) databaseMechanics.getDao(Notification.class);
		try {
			notificationDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void PostInitialize() {
		Bukkit.getPluginManager().registerEvents(new NotificationListener(), TerraCraft.plugin);
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
