package uk.co.terragaming.code.terracraft.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;

public class ChatUtils {
	
	public static List<String> getFilteredTabList(Collection<String> raw, String arg) {
		if (raw == null)
			return Collections.emptyList();
		
		List<String> ret = new ArrayList<String>();
		arg = arg.toLowerCase();
		
		for (String option : raw) {
			if (option.toLowerCase().startsWith(arg)) {
				ret.add(option);
			}
		}
		
		return ret;
	}
	
	public static String getName(Player sender, Channel channel) {
		
		String name = sender.getName();
		if (AccountRegistry.hasAccount(sender)) {
			Account account = AccountRegistry.getAccount(sender);
			name = account.getTerraTag();
			
			if (channel.getName().equals("ooc") || channel.getName().equals("staff"))
				return name;
			
			if (account.getActiveCharacter() != null) {
				Character character = account.getActiveCharacter();
				name = character.getName();
			}
		}
		return name;
	}
	
	public static String getName(Player sender, Integer nameLevel) {
		String name = sender.getName();
		
		if (nameLevel == 0)
			return name;
		
		if (AccountRegistry.hasAccount(sender)) {
			Account account = AccountRegistry.getAccount(sender);
			name = account.getTerraTag();
			if (nameLevel == 1)
				return name;
			
			if (account.getActiveCharacter() != null) {
				Character character = account.getActiveCharacter();
				name = character.getName();
			}
		}
		return name;
	}
}
