package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.EmoteChannel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.SimpleChannel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.commands.ChannelCommands;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners.ChatEventListener;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;

import com.j256.ormlite.dao.Dao;

public class ChatMechanics implements Mechanic{

	public boolean isEnabled()	{ return true; }
	
	public static ChatMechanics getInstance(){
		return (ChatMechanics) TerraCraft.getMechanic("ChatMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private Dao<ChatLogEntry, Integer> chatLogDao;
	
	// Mechanic Methods
	
	public Dao<ChatLogEntry, Integer> getChatLogDao(){
		return chatLogDao;
	}
	
	// Mechanic Events

	@Override
	public void PreInitialize() {
		Bukkit.getPluginManager().registerEvents(new ChatEventListener(), TerraCraft.plugin);
		
		Channel local = new SimpleChannel();
			local.setName("local");
			local.setTag("L");
			local.setRange(100);
			local.autojoin();
		ChannelManager.addChannel(local);
		
		Channel emote = new EmoteChannel();
			emote.setName("emote");
			emote.setRange(local.getRange());
			emote.autojoin();
		ChannelManager.addChannel(emote);
		
		Channel yell = new SimpleChannel();
			yell.setName("yell");
			yell.setTag("L");
			yell.setRange(200);
			yell.autojoin();
		ChannelManager.addChannel(yell);
		
		Channel global = new SimpleChannel();
			global.setName("global");
			global.setTag("G");
			global.autojoin();
		ChannelManager.addChannel(global);
		
		Channel ooc = new SimpleChannel();
			ooc.setName("ooc");
			ooc.setTag("OOC");
		ChannelManager.addChannel(ooc);
		
		Channel staff = new SimpleChannel();
			staff.setName("staff");
			staff.setTag("S");
		ChannelManager.addChannel(staff);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		chatLogDao = (Dao<ChatLogEntry, Integer>) databaseMechanics.getDao(ChatLogEntry.class);
		ChatLogger.init();
	}

	@Override
	public void PostInitialize() {
		CommandRegistry.registerCommands(TerraCraft.plugin, new ChannelCommands());
	}

	@Override
	public void PreDenitialize() {
		
	}

	@Override
	public void Denitialize() {
		ChatLogger.deinit();
	}

	@Override
	public void PostDenitialize() {
		
	}

}
