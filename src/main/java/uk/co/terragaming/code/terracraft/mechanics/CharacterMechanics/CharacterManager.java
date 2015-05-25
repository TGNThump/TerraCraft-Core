package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.events.CharacterChangeEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.j256.ormlite.dao.Dao;

public class CharacterManager {
	
	private static Dao<Character, Integer> charactersDao;
	
	public static void init(){
		charactersDao = CharacterMechanics.getInstance().getCharacterDao();
	}
	
	public static void setActiveCharacter(Account account, Character character) throws SQLException {
		Player player = account.getPlayer();
		
		CharacterChangeEvent event = new CharacterChangeEvent(player, account, character);
		
		TerraCraft.server.getPluginManager().callEvent(event);
		
		// TODO: Items
		
//		 character.getItems().refreshAll();
//		 applyCharacterInventory(player, character);
		 
		player.setHealth(character.getCurHitpoints());
		player.setMaxHealth(character.getMaxHitpoints());
		player.setLevel(character.getCurLevel());
		player.setExp(((float) character.getCurExp()) / 100f);
		player.setFoodLevel(character.getCurHunger());
		player.teleport(character.getLocation());
		player.setGameMode(GameMode.SURVIVAL);
		player.setCustomName(character.getName());
		
		PlayerEffects.removeEffect(player, PlayerEffect.INVISIBLE);
		
		player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "characterChangeInvulnerability")));
		
		OfflinePlayer p = (OfflinePlayer) player;
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				if (!account.getActiveCharacter().equals(character)) return;
				Player player = p.getPlayer();
				PlayerEffects.removeEffect(player, PlayerEffect.INVULNERABLE);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "characterChangeInvulnerabilityExpire")));
			}
			
		}, 60);
		
		player.setCanPickupItems(true);
		PlayerEffects.removeEffect(player, PlayerEffect.INVISIBLE);
		
		account.setActiveCharacter(character);
	}
	
	public static void updateActiveCharacter(Player player, Character character) throws SQLException {
		character.setLocation(player.getLocation());
		character.setCurExp(Math.round(player.getExp() * 100));
		character.setCurHitpoints((int) Math.round(player.getHealth()));
		character.setCurHunger(player.getFoodLevel());
		character.setCurLevel(player.getLevel());
		
		updateCharacter(character);
	}
	
	private static void updateCharacter(Character character) throws SQLException {
		charactersDao.update(character);
//		character.getItems().updateAll();
	}
}
