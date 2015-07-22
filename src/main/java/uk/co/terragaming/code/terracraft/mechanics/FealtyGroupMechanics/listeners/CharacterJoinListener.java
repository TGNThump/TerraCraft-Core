package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.listeners;

import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.events.character.CharacterJoinEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroup;
import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroupManager;


public class CharacterJoinListener implements Listener{

	@EventHandler
	public void onCharacterJoin(CharacterJoinEvent event){
		FealtyGroup group = FealtyGroupManager.getFealtyGroup(event.getCharacter());
		
		if (group != null){
			try {
				CharacterMechanics.getInstance().getCharacterDao().refresh(group.getPatron());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
