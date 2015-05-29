package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

public class NotificationManager {
	
	public static List<Notification> getNotifications(Account account) {
		try {
			Dao<Notification, Integer> notificationDao = NotificationMechanics.getInstance().getNotificationDao();
			return notificationDao.query(notificationDao.queryBuilder().where().eq("accountId", account.getId()).and().isNull("characterId").prepare());
		} catch (SQLException e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}
	}
	
	public static List<Notification> getNotifications(Character character) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("accountId", character.getAccount().getId());
		conditions.put("characterId", character.getId());
		try {
			return NotificationMechanics.getInstance().getNotificationDao().queryForFieldValues(conditions);
		} catch (SQLException e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}
	}
	
	public static void createNotification(Account account, String message) throws TerraException {
		Notification notification = new Notification();
		notification.setAccount(account);
		notification.setMessage(message);
		try {
			NotificationMechanics.getInstance().getNotificationDao().create(notification);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TerraException("Failed to create notification");
		}
	}
	
	public static void createNotification(Character character, String message) throws TerraException {
		Notification notification = new Notification();
		notification.setAccount(character.getAccount());
		notification.setCharacter(character);
		notification.setMessage(message);
		try {
			NotificationMechanics.getInstance().getNotificationDao().create(notification);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TerraException("Failed to create notification");
		}
	}
	
	public static void remove(Notification notification) {
		try {
			NotificationMechanics.getInstance().getNotificationDao().delete(notification);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
