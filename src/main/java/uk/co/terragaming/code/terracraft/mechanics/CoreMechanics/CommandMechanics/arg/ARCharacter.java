package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

public class ARCharacter extends ARAbstractSelect<Character> implements ARAllAble<Character> {
	
	private static ARCharacter i = new ARCharacter();
	
	public static ARCharacter get() {
		return i;
	}
	
	// Override
	
	@Override
	public Character select(String arg, CommandSender sender) throws CommandException {
		
		// Try getting by ID if arg is integer
		
		Dao<Character, Integer> characterDao = CharacterMechanics.getInstance().getCharacterDao();
		
		try {
			Integer id = Integer.parseInt(arg);
			try {
				return characterDao.queryForId(id);
			} catch (SQLException e) {
				throw new CommandException("<b>Could not find a Character with ID " + id);
			}
		} catch (NumberFormatException e){}
		
		// If not, try getting by name...
		
		List<Character> chars = Lists.newArrayList();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = AccountRegistry.getAccount(player);
			if (account == null) continue;
			Character character = account.getActiveCharacter();
			if (character == null) continue;
			chars.add(character);
		}
		
		String name = arg.toLowerCase();
		
		for (Character c : chars){
			if (c.getName().toLowerCase().equals(name)) return c;
		}
		
		throw new CommandException("<b>Could not find online character with name <n>" + arg);
		
	}
	
	@Override
	public Collection<String> altNames(CommandSender sender) {
		List<String> ret = Lists.newArrayList();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = AccountRegistry.getAccount(player);
			if (account == null) continue;
			Character character = account.getActiveCharacter();
			if (character == null) continue;
			ret.add(character.getName());
		}
		
		return ret;
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> ret = Lists.newArrayList();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = AccountRegistry.getAccount(player);
			if (account == null) continue;
			Character character = account.getActiveCharacter();
			if (character == null) continue;
			ret.add(character.getName());
		}
		
		return ret;
	}
	
	@Override
	public Collection<Character> getAll(CommandSender sender) {
		List<Character> chars = Lists.newArrayList();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			Account account = AccountRegistry.getAccount(player);
			if (account == null) continue;
			Character character = account.getActiveCharacter();
			if (character == null) continue;
			chars.add(character);
		}
		
		return chars;
	}
}
