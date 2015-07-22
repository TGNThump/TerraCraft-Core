package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.commands.StaffItemCommands;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.events.ItemEvents;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV1.events.ItemUseEvents;
import uk.co.terragaming.code.terracraft.utils.AttributeUtil;

import com.j256.ormlite.dao.Dao;

public class ItemMechanicsV1 implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return false;
	}
	
	public static ItemMechanicsV1 getInstance() {
		return (ItemMechanicsV1) TerraCraft.getMechanic("ItemMechanicsV1");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private Dao<Item, Integer> itemDao;
	private Dao<ItemInstance, Integer> itemInstanceDao;
	private ItemRegistry itemRegistry;
	private ItemInstanceRegistry itemInstanceRegistry;
	private UUID uuid;
	
	// Mechanic Methods
	
	public Dao<Item, Integer> getItemDao() {
		return itemDao;
	}
	
	public Dao<ItemInstance, Integer> getItemInstanceDao() {
		return itemInstanceDao;
	}
	
	public ItemRegistry getItemRegistry() {
		return itemRegistry;
	}
	
	public ItemInstanceRegistry getItemInstanceRegistry() {
		return itemInstanceRegistry;
	}
	
	public UUID getAttributeUUID() {
		return uuid;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		uuid = AttributeUtil.computeUUID("TerraGamingNetwork-TerraCraft");
		itemRegistry = new ItemRegistry();
		itemInstanceRegistry = new ItemInstanceRegistry();
		Bukkit.getPluginManager().registerEvents(new ItemEvents(), TerraCraft.plugin);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		itemDao = (Dao<Item, Integer>) databaseMechanics.getDao(Item.class);
		itemInstanceDao = (Dao<ItemInstance, Integer>) databaseMechanics.getDao(ItemInstance.class);
		try {
			itemDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			for (Iterator<Item> iter = itemDao.iterator(); iter.hasNext();) {
				Item item = iter.next();
				itemRegistry.addItem(item.getId(), item);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		List<ItemInstance> items;
		try {
			items = itemInstanceDao.query(itemInstanceDao.queryBuilder().where().isNull("charId").prepare());
			for (ItemInstance item : items) {
				itemInstanceRegistry.addItemToDroped(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Bukkit.getPluginManager().registerEvents(new ItemUseEvents(), TerraCraft.plugin);
//		Bukkit.getPluginManager().registerEvents(new ItemBindEvents(), TerraCraft.plugin);
	}
	
	@Override
	public void PostInitialize() {
		CommandRegistry.registerCommands(TerraCraft.plugin, new StaffItemCommands());
	}
	
	@Override
	public void PreDenitialize() {
		
	}
	
	@Override
	public void Denitialize() {
		
	}
	
	@Override
	public void PostDenitialize() {
		
	}
	
}
