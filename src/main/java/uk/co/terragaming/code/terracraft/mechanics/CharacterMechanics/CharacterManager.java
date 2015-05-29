package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.events.CharacterChangeEvent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

public class CharacterManager {
	
	private static Dao<Character, Integer> charactersDao;
	
	public static void init(){
		charactersDao = CharacterMechanics.getInstance().getCharacterDao();
	}
	
	public static void setActiveCharacter(Account account, Character character) throws SQLException {

		downloadCharacter(character, account);
		
		Player player = account.getPlayer();
		
		CharacterChangeEvent event = new CharacterChangeEvent(player, account, character);
		
		TerraCraft.server.getPluginManager().callEvent(event);
		
		character.getItems().refreshCollection();
		
		player.setHealth(character.getCurHitpoints());
		
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		registry.clearItems(character);
		player.getInventory().clear();
		
		if (!player.isDead()){
			applyCharacterInventory(player, character);
		}
			
		player.setMaxHealth(character.getMaxHitpoints());
		player.setLevel(character.getCurLevel());
		player.setExp(((float) character.getCurExp()) / 100f);
		player.setFoodLevel(character.getCurHunger());
		player.setExhaustion(character.getCurExhaustion());
		player.setSaturation(character.getCurSaturation());
		player.teleport(character.getLocation());
		player.setGameMode(GameMode.SURVIVAL);
		player.setCustomName(character.getName());
		
		if (!player.isDead())
			PlayerEffects.removeEffect(player, PlayerEffect.INVISIBLE);
		
		player.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "characterChangeInvulnerability")));
		
		OfflinePlayer p = (OfflinePlayer) player;
		
		Bukkit.getScheduler().runTaskLater(TerraCraft.plugin, new Runnable(){

			@Override
			public void run() {
				if (account.getActiveCharacter() == null) return;
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
	
	private static void applyCharacterInventory(Player player, Character character) {
		PlayerInventory inventory = player.getInventory();
		
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		
		ItemStack[] armour = new ItemStack[4];
		
		List<ItemInstance> addLater = Lists.newArrayList();
		for (ItemInstance item : character.getItems()){
			registry.addItemToCharacter(character, item);
			if (item.getSlotId() == null){ addLater.add(item); continue; }
			
			if (item.getSlotId() >= inventory.getSize()){
				armour[item.getSlotId() - inventory.getSize()] = item.getItemStack();
				continue;
			}
			
			inventory.setItem(item.getSlotId(), item.getItemStack());
		}
		for (ItemInstance item : addLater){
			inventory.addItem(item.getItemStack());
		}
		inventory.setArmorContents(armour);
	}

	public static void updateActiveCharacter(Player player, Character character) throws SQLException {
		character.setLocation(player.getLocation());
		character.setCurExp(Math.round(player.getExp() * 100));
		character.setCurHitpoints((int) Math.round(player.getHealth()));
		character.setCurHunger(player.getFoodLevel());
		character.setCurExhaustion((int) player.getExhaustion());
		character.setCurSaturation((int) player.getSaturation());
		character.setCurLevel(player.getLevel());
		
		if (!player.isDead())
			updateCharacterInventory(player, character);
		
		updateCharacter(character);
	}
	
	private static void updateCharacterInventory(Player player, Character character) {
		PlayerInventory inv = player.getInventory();
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		
		List<ItemInstance> toUpdate = Lists.newArrayList();
		
		// Get the items in the characters itemregistry ...
		@SuppressWarnings("unchecked")
		ArrayList<Integer> items = (ArrayList<Integer>) registry.getItemIds(character).clone();
		
		// ... then for each slot ...
		for (int slot = 0; slot < inv.getSize() + 4; slot++){
			ItemStack is = null;
			if (slot >= inv.getSize()){
				is = inv.getArmorContents()[slot - inv.getSize()];
			} else {
				is = inv.getItem(slot);
			}
			// If the item is an ItemInstance ...
			
			if (is == null) continue;
			if (is.getType().equals(Material.AIR)) continue;
			Integer id = ItemManager.getItemInstanceId(is);
			if (id == null) continue;
			
			// If the item is in the characters itemregistry ...
			if (items.contains(id)){
				ItemInstance item = registry.getItem(id);
				item.setSlotId(slot);
				item.setCharacter(character);
				item = ItemManager.updateItemInstance(item, is);
				items.remove(id);
				toUpdate.add(item);
			// ... or if it is not ...
			} else {
				if (registry.hasItem(id)){
					ItemInstance item = registry.getItem(id);
					item.setSlotId(slot);
					item.setCharacter(character);
					item = ItemManager.updateItemInstance(item, is);
					registry.removeItemFromDropped(item);
					registry.addItemToCharacter(character, item);
					toUpdate.add(item);
				} else {
					TerraLogger.error("Could not find item with id " + id + ".");
				}
			}
		}
		
		// If there are any items in the registry that were not in the inventory ...
		if (items.size() > 0){
			for (Iterator<Integer> iter = items.iterator(); iter.hasNext();){
				Integer id = iter.next();
				ItemInstance item = registry.getItem(id);
				item.setCharacter(null);
				item.setSlotId(null);
				registry.removeFromCharacter(character, id);
				registry.addItemToDroped(item);
				toUpdate.add(item);
			}
		}
		
		try {
			ItemMechanics.getInstance().getItemInstanceDao().callBatchTasks(new Callable<Void>(){

				@Override
				public Void call() throws Exception {
					Dao<ItemInstance, Integer> dao = ItemMechanics.getInstance().getItemInstanceDao();
					for (ItemInstance item : toUpdate)
						dao.update(item);
					return null;
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void updateCharacter(Character character) throws SQLException {
		charactersDao.update(character);
		
		TerraLogger.info("Uploaded Character Data of " + character.getAccount().getTerraTag() + "'s" + character.getName() + ".");
	}
	
	private static void downloadCharacter(Character character, Account account) throws SQLException {
		charactersDao.refresh(character);
		charactersDao.refresh(character.getPatron());
		
		TerraLogger.info("Downloaded Character Data of " + account.getTerraTag() + "'s" + character.getName() + ".");
	}
}
