package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.events.account.AccountLoginEvent;
import uk.co.terragaming.code.terracraft.events.account.AccountPostLoginEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces.CharacterSelectInterface;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class LoginListener implements Listener {

	@EventHandler
	public void onAccountLogin(AccountLoginEvent event){
		Account account = event.getAccount();
		Player player = account.getPlayer();
		try {
			account.getCharacters().refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
			player.kickPlayer(Txt.parse(Lang.get(account.getLanguage(), "internalException")));
			return;
		}
	}
	
	@EventHandler
	public void onAccountPostLogin(AccountPostLoginEvent event){
		Account account = event.getAccount();
		Player player = account.getPlayer();
		
		new CharacterSelectInterface(player);
	}
	
}
