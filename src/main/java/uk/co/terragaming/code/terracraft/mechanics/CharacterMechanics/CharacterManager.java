package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.events.character.CharacterJoinEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.LoadingMode;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics.NickRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.utils.ItemUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;


public class CharacterManager {
	
	private static Dao<Character, Integer> charactersDao;

	public static void init() {
		charactersDao = CharacterMechanics.getInstance().getCharacterDao();
	}
	
	// Download
	
	public static void setActiveCharacter(Account account, Integer charId){
		Player player = account.getPlayer();
		LoadingMode.activeFor(player);
		
		Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				Character character = downloadCharacter(account, charId);
				applyCharacter(account, character);
			}
			
		});
	}
	
	public static void setActiveCharacter(Account account, Character character){
		Player player = account.getPlayer();
		LoadingMode.activeFor(player);
		
		Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, new Runnable(){

			@Override
			public void run(){
				downloadCharacter(account, character);
				applyCharacter(account, character);
			}
			
		});
	}
	
	public static Character downloadCharacter(Account account, Integer charId){
		try {
			Character character = CharacterMechanics.getInstance().getCharacterDao().queryForId(charId);
			downloadCharacter(account, character);
			return character;
		} catch (SQLException e) {
			// TODO: Error Recovery
			kickPlayerInternalException(account.getPlayer());
			e.printStackTrace();
			return null;
		}
	}
	
	public static void downloadCharacter(Account account, Character character){
		try{
			charactersDao.refresh(character);
			charactersDao.refresh(character.getPatron());
			
			downloadCharacterInventory(character);
			
			TerraLogger.info("Downloaded Character Data of <n>" + character.getAccount().getTerraTag() + "<r>'s <n>" + character.getName() + "<r>.");
		} catch (SQLException e) {
			// TODO: Error Recovery
			kickPlayerInternalException(account.getPlayer());
			e.printStackTrace();
		}
	}
	
	public static void applyCharacter(Account account, Character character){
		Player player = account.getPlayer();
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				try{
					applyCharacterSync(account, character);
					applyCharacterInventory(account, character);
					LoadingMode.deactiveFor(player);
					CharacterJoinEvent e = new CharacterJoinEvent(character, player);
					Bukkit.getPluginManager().callEvent(e);
					PlayerEffects.addEffect(player, PlayerEffect.INVULNERABLE);
					player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "characterChangeInvulnerability")));
				} catch (Exception e){
					// TODO: Error Recovery
					kickPlayerInternalException(account.getPlayer());
					e.printStackTrace();
				}
			}
			
		});
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				if (account.getActiveCharacter() == null) return;
				if (!account.getActiveCharacter().equals(character)) return;
				PlayerEffects.removeEffect(player, PlayerEffect.INVULNERABLE);
				player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "characterChangeInvulnerabilityExpire")));
			}
			
		}, 60);
	}
	
	private static void applyCharacterSync(Account account, Character character){
		Player player = account.getPlayer();
		
		player.setHealth(character.getCurHitpoints());
		player.setMaxHealth(character.getMaxHitpoints());
		player.setLevel(character.getCurLevel());
		player.setExp((float) character.getCurExp() / 100f);
		player.setFoodLevel(character.getCurHunger());
		player.setExhaustion(character.getCurExhaustion());
		player.setSaturation(character.getCurSaturation());
		player.teleport(character.getLocation());
		player.setCustomName(character.getName());
		
		NickRegistry.setNick(player.getUniqueId(), character.getColouredName());
		account.setActiveCharacter(character);

	}
	
	private static void downloadCharacterInventory(Character character) throws SQLException{
		character.getItems().refreshCollection();
		for (ItemInstance item : character.getItems()){
			item.download();
		}
	}
	
	private static void applyCharacterInventory(Account account, Character character){
		Player player = account.getPlayer();
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		
		ItemStack[] armour = new ItemStack[4];
		
		List<ItemInstance> addLater = Lists.newArrayList();
		for (ItemInstance item : character.getItems()) {
			ItemInstanceRegistry.addItemToCharacter(character, item);
			
			if (item.getSlotId() == null) {
				addLater.add(item);
				continue;
			}
			
			if (item.getSlotId() >= inventory.getSize()) {
				armour[item.getSlotId() - inventory.getSize()] = item.render();
				continue;
			}
			
			inventory.setItem(item.getSlotId(), item.render());
		}
		for (ItemInstance item : addLater) {
			inventory.addItem(item.render());
		}
		inventory.setArmorContents(armour);
	}
	
	// Upload
	
	public static void updateActiveCharacter(Account account, Character character){
		Player player = account.getPlayer();
		
		character.setLocation(player.getLocation());
		character.setCurExp(Math.round(player.getExp() * 100));
		character.setCurHitpoints((int) Math.round(player.getHealth()));
		character.setCurHunger(player.getFoodLevel());
		character.setCurExhaustion((int) player.getExhaustion());
		character.setCurSaturation((int) player.getSaturation());
		character.setCurLevel(player.getLevel());
		
		LoadingMode.activeFor(player);
		
		List<ItemInstance> toUpload = updateCharacterItems(account, character);
		
		Bukkit.getScheduler().runTaskAsynchronously(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				try{
					if (!player.isDead()) {
						uploadItems(toUpload);
					}
					
					uploadCharacter(account, character);
					
				} catch (SQLException e) {
					// TODO: Error Recovery
					kickPlayerInternalException(account.getPlayer());
					e.printStackTrace();
					return;
				}
			}
			
		});

	}
	
	private static List<ItemInstance> updateCharacterItems(Account account, Character character){
		Player player = account.getPlayer();
		PlayerInventory inv = player.getInventory();
		
		List<ItemInstance> toUpdate = Lists.newArrayList();
		ArrayList<Integer> items = ItemInstanceRegistry.getCharactersItemIds(character);
		
		for (int slot = 0; slot < inv.getSize() + 4; slot++) {
			// Get the item stack ...
			ItemStack is = null;
			if (slot >= inv.getSize()) {
				is = inv.getArmorContents()[slot - inv.getSize()];
			} else {
				is = inv.getItem(slot);
			}
			// ... and if the item is an ItemInstance ...
			
			if (is == null) continue;
			if (is.getType().equals(Material.AIR)) continue;
			
			Integer id = ItemUtils.getItemId(is);
			if (id == null) continue;
			
			// If the item is in the characters itemregistry ...
			if (items.contains(id)) {
				ItemInstance item = ItemInstanceRegistry.get(id);
				item.setSlotId(slot);
				items.remove(id);
				toUpdate.add(item);
				// ... or if it is not ...
			} else {
				if (ItemInstanceRegistry.has(id)) {
					ItemInstance item = ItemInstanceRegistry.get(id);
					item.setSlotId(slot);
					item.setOwner(character);
					ItemInstanceRegistry.removeItemFromDropped(item);
					ItemInstanceRegistry.addItemToCharacter(character, item);
					toUpdate.add(item);
				} else {
					TerraLogger.error("Could not find item with id " + id + ".");
				}
			}
		}
		
		// If there are any items in the registry that were not in the inventory
		if (items.size() > 0) {
			for (Integer id : items) {
				ItemInstance item = ItemInstanceRegistry.get(id);
				item.setOwner(null);
				item.setSlotId(null);
				ItemInstanceRegistry.removeItemFromCharacter(character, item);
				ItemInstanceRegistry.addItemToDropped(item);
				toUpdate.add(item);
			}
		}
		
		return toUpdate;
	}
	
	public static void uploadItems(List<ItemInstance> items) throws SQLException{
		for (ItemInstance item : items) {
			item.upload();
		}
	}
	
	private static void uploadCharacter(Account account, Character character) throws SQLException{
		charactersDao.update(character);
		TerraLogger.info("Uploaded Character Data of <n>" + character.getAccount().getTerraTag() + "<r>'s <n>" + character.getName() + "<r>.");
	}
	
	// Util
	
	private static void kickPlayerInternalException(Player player){
		Bukkit.getScheduler().callSyncMethod(TerraCraft.plugin, new Callable<Boolean>(){

			@Override
			public Boolean call() throws Exception {
				player.kickPlayer(Lang.get("internalException"));
				return true;
			}

		});
	}
}
