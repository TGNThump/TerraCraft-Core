package uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.Channel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.Callback;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class FealtyManager {
	
	public static boolean hasVassalRecursively(Character you, Character them){
		if (you.getVassals().isEmpty())
			return false;
		
		if (you.getVassals().contains(them))
			return true;
		
		for(Character vassal : you.getVassals()){
			if (hasVassalRecursively(vassal, them))
				return true;
		}
		
		return false;
	}
	
	public static List<Character> getVassalsRecursively(Character character){
		if (character.getVassals().isEmpty())
			return Lists.newArrayList();
		
		List<Character> ret = Lists.newArrayList();
		for(Character c : character.getVassals()){
			ret.add(c);
			ret.addAll(getVassalsRecursively(c));
		}
		return ret;
	}
	
	public static void fixVassalDiscrepancy(Character you, Character them){
		Character theirPatron = null;
		
		for (Character vassal : you.getVassals()){
			if ((!vassal.equals(them)) & (!hasVassalRecursively(you, them))) continue;
			theirPatron = vassal;
			break;
		}
		
		if (theirPatron == null){ TerraLogger.error("fixVassalDiscrepency called when no discrepancy exists..."); return; }
		
		theirPatron.setPatron(null);
		
		try {
			CharacterMechanics.getInstance().getCharacterDao().update(theirPatron);
			theirPatron.getVassals().refreshCollection();
			you.getVassals().refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Account account = theirPatron.getAccount();
		Player player = account.getPlayer();
		// TODO: Add notification system
		if (player == null) return;
		Language lang = Language.ENGLISH;
		if (account.getLanguage() != null)
			lang = account.getLanguage();
		
		player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "fealtyPatronBecomeVassal")));
	}
		
	@Callback // Used in characterShiftClickInterface
	public static void swearFealty(Player you, Player target){
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		Account yourAcc = registry.getAccount(you);
		
		Character yourChar = yourAcc.getActiveCharacter();
		Character targetChar = registry.getAccount(target).getActiveCharacter();
		
		// Do Checks to prevent infinite loops in recursive vassal getting.
		
		if (hasVassalRecursively(yourChar, targetChar)){
			fixVassalDiscrepancy(yourChar, targetChar);
		}
		
		yourChar.setPatron(targetChar);
		
		try {
			CharacterMechanics.getInstance().getCharacterDao().update(yourChar);
			targetChar.getVassals().refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		swearFealtyText(you, yourChar, target, targetChar);
	}
	
	public static void swearFealtyText(Player you, Character yourChar, Player target, Character targetChar){
		Channel local = ChannelManager.getChannel(0);

		for (Entity entity : you.getNearbyEntities(local.getRange(), 500, local.getRange())){
			if (!(entity instanceof Player)) continue;
			Player reciever = (Player) entity;
			if (local.contains(reciever.getUniqueId())){
				reciever.sendMessage("");
			}
		}
		
		int delay = 40;
		
		local.processChatEvent(you, "In the name and sight of the Gods,");
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){
			public void run() {
				local.processChatEvent(you, "I, " + yourChar.getName() + ",");
			}
		}, delay * 1);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){
			public void run() {
				local.processChatEvent(you, "from this day, until my last day,");
			}
		}, delay * 2);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){
			public void run() {
				local.processChatEvent(you, "pledge my sword and my soul,");
			}
		}, delay * 3);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){
			public void run() {
				local.processChatEvent(you, "my land and my life,");
			}
		}, delay * 4);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){
			public void run() {
				local.processChatEvent(you, "to the service and defence of");
			}
		}, delay * 5);
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){
			public void run() {
				local.processChatEvent(you, targetChar.getName() + ".");
				
				for (Entity entity : you.getNearbyEntities(local.getRange(), 500, local.getRange())){
					if (!(entity instanceof Player)) continue;
					Player reciever = (Player) entity;
					if (local.contains(reciever.getUniqueId())){
						reciever.sendMessage("");
					}
				}
			}
		}, delay * 6);
	}
	
	@Callback // Used in characterShiftClickInterface
	public static void breakFealty(Player you, Player target){
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		Account yourAcc = registry.getAccount(you);
		Account targetAcc = registry.getAccount(target);
		
		Character yourChar = yourAcc.getActiveCharacter();
		Character targetChar = registry.getAccount(target).getActiveCharacter();
		
		yourChar.setPatron(null);
		
		try {
			CharacterMechanics.getInstance().getCharacterDao().update(yourChar);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		you.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>" + Lang.get(yourAcc.getLanguage(), "characterBreakFealty"), targetChar.getName()));
		target.sendMessage(Txt.parse("[<l>TerraCraft<r>] <b>" + Lang.get(targetAcc.getLanguage(), "characterBreakFealtyToYou"), yourChar.getName()));
	}
}
