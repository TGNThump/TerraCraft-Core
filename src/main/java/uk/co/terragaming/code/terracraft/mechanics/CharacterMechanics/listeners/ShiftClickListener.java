package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces.CharacterShiftClickInterface;

public class ShiftClickListener implements Listener{

	@EventHandler
	public void onShiftClick(PlayerInteractEntityEvent event){
		if(!(event.getRightClicked() instanceof Player)) return;
		Player you = event.getPlayer();
		Player target = (Player) event.getRightClicked();
		
		if (!you.isSneaking()) return;
		
		new CharacterShiftClickInterface(you, target);
	}
	
}
