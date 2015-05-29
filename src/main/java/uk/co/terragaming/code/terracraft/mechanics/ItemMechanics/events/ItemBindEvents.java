package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.events;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstance;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemManager;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class ItemBindEvents implements Listener{

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.PLAYER)) return;
		
		AccountRegistry accRegistry = AccountMechanics.getInstance().getRegistry();
		Player player = (Player) event.getEntity();
		if (!accRegistry.hasAccount(player)) return;
		Account account = accRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		
		if (character == null){
			event.getDrops().clear();
			event.setDroppedExp(0);
			return;
		}
		
		List<ItemStack> dropList = event.getDrops();
		Iterator<ItemStack> iter = dropList.iterator();
		ItemStack is;
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		List<ItemInstance> items = Lists.newArrayList();
		
		while (iter.hasNext()) {
			is = iter.next();

			if (!ItemManager.isItemInstance(is)) continue;
			Integer id = ItemManager.getItemInstanceId(is);
			ItemInstance item = registry.getItem(id);
			
			switch (item.getBinding()){
				case NONE:
					item.setCharacter(null);
					item.setSlotId(null);
					registry.removeFromCharacter(character, id);
					registry.addItemToDroped(item);
					items.add(item);
					break;
				default:
					registry.removeFromCharacter(character, id);
					item.setCharacter(character);
					item.setSlotId(null);
					items.add(item);
					iter.remove();
					break;
			}
		}
		
		try {
			ItemMechanics.getInstance().getItemInstanceDao().callBatchTasks(new Callable<Void>(){

				@Override
				public Void call() throws Exception {
					for (ItemInstance item : items)
						ItemMechanics.getInstance().getItemInstanceDao().update(item);
					return null;
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		AccountRegistry accRegistry = AccountMechanics.getInstance().getRegistry();
		Player player = event.getPlayer();
		if (!accRegistry.hasAccount(player)) return;
		Account account = accRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		if (character == null) return;
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		
		PlayerEffects.removeEffect(event.getPlayer(), PlayerEffect.INVISIBLE);
		
		player.getInventory().clear();
		
		try {
			character.getItems().refreshCollection();
			
			PlayerInventory inventory = player.getInventory();
			
			for (ItemInstance item : character.getItems()){
				inventory.addItem(item.getItemStack());
				registry.addItemToCharacter(character, item);
			}
			
			ItemMechanics.getInstance().getItemInstanceDao().callBatchTasks(new Callable<Void>(){

				@Override
				public Void call() throws Exception {
					for (ItemInstance item : character.getItems())
						ItemMechanics.getInstance().getItemInstanceDao().update(item);
					return null;
				}
				
			});
			
			CharacterMechanics.getInstance().getCharacterDao().update(character);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event){
		if (event.isCancelled()) return;
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		Item i = event.getEntity();
		if (!ItemManager.isItemInstance(i.getItemStack())) return;
		Integer id = ItemManager.getItemInstanceId(i.getItemStack());
		ItemInstance item = registry.getItem(id);
		
		if (item.getCharacter() == null) return;
		
		registry.removeFromCharacter(item.getCharacter(), item.getId());
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event){
		if (event.isCancelled()) return;
		ItemInstanceRegistry registry = ItemMechanics.getInstance().getItemInstanceRegistry();
		Item i = event.getItem();
		if (!ItemManager.isItemInstance(i.getItemStack())) return;
		Integer id = ItemManager.getItemInstanceId(i.getItemStack());
		ItemInstance item = registry.getItem(id);
		
		AccountRegistry accRegistry = AccountMechanics.getInstance().getRegistry();
		Player player = event.getPlayer();
		if (!accRegistry.hasAccount(player)) return;
		Account account = accRegistry.getAccount(player);
		Character character = account.getActiveCharacter();
		if (character == null) return;
		
		if (!(item.getBinding() == null)){
			switch(item.getBinding()){
			case ACCOUNT:
				event.setCancelled(!account.equals(item.getBoundAccount()));
				return;
			case CHARACTER:
				event.setCancelled(!character.equals(item.getBoundCharacter()));
				return;
			default: break;
			}
			
		}
		
		if (item.getItem().getBindType() == null) return;
		
		if (item.getItem().getBindType().equals(ItemBindType.PICKUP)){
			item.setBinding(ItemBinding.CHARACTER);
			item.setBoundCharacter(character);
			ItemStack is = item.getItemStack();
			event.getItem().setItemStack(is);
			event.getPlayer().sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "itemBindOnPickup"), item.getColouredName()));
		}
		
		if (item.getItem().getBindType().equals(ItemBindType.ACCOUNT)){
			item.setBinding(ItemBinding.ACCOUNT);
			item.setBoundAccount(account);
			ItemStack is = item.getItemStack();
			event.getItem().setItemStack(is);
			event.getPlayer().sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(account.getLanguage(), "itemBindAccountOnPickup"), item.getColouredName()));
		}
		
	}
	
}
