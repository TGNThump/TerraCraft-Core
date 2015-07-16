package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

public class ChatLogger implements Runnable {
	
	private static final int PERIOD = 6000; // 5 mins in ticks
	
	public static List<ChatLogEntry> entries = Lists.newArrayList();
	private static ChatLogger instance;
	
	public static void init() {
		instance = new ChatLogger();
		Bukkit.getScheduler().runTaskTimer(TerraCraft.plugin, instance, PERIOD, PERIOD);
	}
	
	public static void log(Account account, Character character, Channel channel, String message) {
		ChatLogEntry entry = new ChatLogEntry();
		entry.setAccount(account);
		entry.setCharacter(character);
		entry.setChannel(channel.getName());
		entry.setMessage(message);
		entry.setTimestamp(DateTime.now());
		entry.setServer(TerraCraft.getServerName().toUpperCase());
		
		entries.add(entry);
	}
	
	public static void deinit() {
		instance.run();
	}
	
	@Override
	public void run() {
		if (entries.size() == 0) return;
		Dao<ChatLogEntry, Integer> logDao = ChatMechanics.getInstance().getChatLogDao();
		try {
			logDao.callBatchTasks(new Callable<Void>() {
				
				@Override
				public Void call() throws Exception {
					
					for (Iterator<ChatLogEntry> iter = entries.iterator(); iter.hasNext();) {
						ChatLogEntry entry = iter.next();
						logDao.create(entry);
						iter.remove();
					}
					
					return null;
				}
			});
			TerraLogger.info("Uploaded Chat to Database.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
