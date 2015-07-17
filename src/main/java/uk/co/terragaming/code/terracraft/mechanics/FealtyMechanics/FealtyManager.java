package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.events.character.fealty.FealtyBreakEvent;
import uk.co.terragaming.code.terracraft.events.character.fealty.FealtySwearEvent;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.Callback;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics.NotificationManager;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroup;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroupManager;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroupRegistry;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;

public class FealtyManager {
	
	// Utils
	
	public static List<Character> getVassalsRecursively (Character character){
		Long timestamp = System.currentTimeMillis();
		
		if (character == null) return Lists.newArrayList();
		if (character.getVassals().isEmpty()) return Lists.newArrayList();
		
		List<Character> ret = Lists.newArrayList();
		for (Character c : character.getVassals()){
			ret.add(c);
			ret.addAll(getVassalsRecursively(c));
		}
		
		Long timeTaken = System.currentTimeMillis() - timestamp;
		if (timeTaken > 50){
			TerraLogger.warn("getVassalsRecursively(" + character.getId() + ") took " + timeTaken + "ms");
		}
				
		return ret;
	}
	
	public static Character getPatronRecursivly(Character character){
		if (character.getPatron() == null) return character;
		return getPatronRecursivly(character.getPatron());
	}
	
	public static boolean isVassalRecursive(Character you, Character them){
		if (you == null) return false;
		if (them == null) return false;
		if (you.getVassals().isEmpty()) return false;
		if (you.getVassals().contains(them)) return true;
		
		for (Character vassal : you.getVassals()){
			if (isVassalRecursive(vassal, them)) return true;
		}
		
		return false;
	}
	
	public static Character getPatronBellowYou(Character vassal, Character you){
		if (vassal.getPatron() == null) return null;
		if (vassal.getPatron().equals(you)) return vassal;
		return getPatronBellowYou(vassal.getPatron(), you);
	}
	
	public static void fixVassalDiscrepancy(Character you, Character them){
		// Get the targets patron recursively, but stop one level bellow you, ...
		Character newGroupLeader = getPatronBellowYou(them, you);
		if (newGroupLeader == null){
			TerraLogger.error("fixVassalDiscrepency called when no discrepancy exists...");
			return;
		}
		
		// ... set the newGroupLeaders patron to null and create a new fealty group ...
		newGroupLeader.setPatron(null);
		
		FealtyGroup group = FealtyGroupManager.createFealtyGroup(newGroupLeader);
		if(group == null) return;
		
		// ... update all the characters data ...
		
		try{
			CharacterMechanics.getInstance().getCharacterDao().update(newGroupLeader);
			newGroupLeader.getVassals().refreshCollection();
			you.getVassals().refreshCollection();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		// ... and send a notification to the new groups leader ...
		
		Account account = newGroupLeader.getAccount();
		
		try {
			NotificationManager.createNotification(newGroupLeader, Lang.get(account.getLanguage(), "fealtyPatronBecomeVassal"));
		} catch (TerraException e) {
			e.printStackTrace();
		}
	}
	
	// Swear and Break Fealty
	
	@Callback
	public static void swearFealty(Player you, Player target){
		Character yourChar = AccountRegistry.getAccount(you).getActiveCharacter();
		Character targetChar = AccountRegistry.getAccount(target).getActiveCharacter();
		
		swearFealty(yourChar, targetChar);
	}
	
	public static void swearFealty(Character you, Character target){
		FealtySwearEvent e1 = new FealtySwearEvent(you, target);
		Bukkit.getPluginManager().callEvent(e1);
		if (e1.isCancelled()) return;
		
		// If your target is one of your vassals ...
		if (isVassalRecursive(you, target)){
			fixVassalDiscrepancy(you, target);
		}
		
		if (you.getPatron() != null)
			breakFealty(you, you.getPatron());
		
		if (FealtyGroupRegistry.isGroupPatron(you)){
			FealtyGroupManager.destroyFealtyGroup(you);
		}
		
		you.setPatron(target);
		
		if (!FealtyGroupRegistry.isGroupPatron(target) && target.getPatron() == null){
			FealtyGroupManager.createFealtyGroup(target);
		}
		
		try {
			CharacterMechanics.getInstance().getCharacterDao().update(you);
			target.getVassals().refreshCollection();
		} catch (SQLException e){
			e.printStackTrace();
		}
	
		if (you.getAccount().getPlayer() == null || target.getAccount().getPlayer() == null){
			// TODO: Lang...
			try {
				NotificationManager.createNotification(you, "You swore fealty to <n>" + target.getName() + "<r>.");
				NotificationManager.createNotification(target, "<n>" + you.getName() + " swore fealty to you.");
			} catch (TerraException e) {
				e.printStackTrace();
			}
		} else swearFealtyText(you.getAccount().getPlayer(), you, target.getAccount().getPlayer(), target);
	}
	
	public static void swearFealtyText(Player you, Character yourChar, Player target, Character targetChar) {
		Channel local = ChannelManager.getChannel(0);
		
		for (Entity entity : you.getNearbyEntities(local.getRange(), 500, local.getRange())) {
			if (!(entity instanceof Player)) {
				continue;
			}
			Player reciever = (Player) entity;
			if (local.contains(reciever.getUniqueId())) {
				reciever.sendMessage("");
			}
		}
		
		int delay = 40;
		
		local.processChatEvent(you, "In the name and sight of the Gods,");
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				local.processChatEvent(you, "I, " + yourChar.getName() + ",");
			}
		}, delay * 1);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				local.processChatEvent(you, "from this day, until my last day,");
			}
		}, delay * 2);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				local.processChatEvent(you, "pledge my sword and my soul,");
			}
		}, delay * 3);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				local.processChatEvent(you, "my land and my life,");
			}
		}, delay * 4);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				local.processChatEvent(you, "to the service and defence of");
			}
		}, delay * 5);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				local.processChatEvent(you, targetChar.getName() + ".");
				
				for (Entity entity : you.getNearbyEntities(local.getRange(), 500, local.getRange())) {
					if (!(entity instanceof Player)) {
						continue;
					}
					Player reciever = (Player) entity;
					if (local.contains(reciever.getUniqueId())) {
						reciever.sendMessage("");
					}
				}
			}
		}, delay * 6);
	}
	
	@Callback
	public static void breakFealty(Player you, Player target){
		Character yourChar = AccountRegistry.getAccount(you).getActiveCharacter();
		Character targetChar = AccountRegistry.getAccount(target).getActiveCharacter();
		
		breakFealty(yourChar, targetChar);
	}
	
	public static void breakFealty(Character you, Character target){
		FealtyBreakEvent e1 = new FealtyBreakEvent(you, target);
		Bukkit.getPluginManager().callEvent(e1);
		if (e1.isCancelled()) return;
		
		try {
			NotificationManager.createNotification(you, "<b>" + Lang.get(you.getAccount().getLanguage(), "characterBreakFealty"), target.getName());
			NotificationManager.createNotification(target, "<b>" + Lang.get(target.getAccount().getLanguage(), "characterBreakFealtyToYou"), you.getName());
		} catch (TerraException e2) {
			e2.printStackTrace();
		}

		Character oldPatron = you.getPatron();
		
		you.setPatron(null);
		if (you.getVassals().size() > 0) FealtyGroupManager.createFealtyGroup(you);
		
		if (FealtyGroupRegistry.isGroupPatron(oldPatron)){
			if (oldPatron.getVassals().size() <= 1){
				FealtyGroupManager.destroyFealtyGroup(oldPatron);
			}
		}
		
		try{
			CharacterMechanics.getInstance().getCharacterDao().update(you);
			oldPatron.getVassals().refreshCollection();
			target.getVassals().refreshCollection();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}
}
