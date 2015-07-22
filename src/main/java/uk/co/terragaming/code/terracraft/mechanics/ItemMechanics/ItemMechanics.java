package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.sql.SQLException;
import java.util.UUID;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.commands.StaffItemCommands;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemComponentRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries.ItemInstanceRegistry;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.ItemComponentData;
import uk.co.terragaming.code.terracraft.utils.AttributeUtil;

import com.j256.ormlite.dao.Dao;


public class ItemMechanics implements Mechanic{
	
	@Override
	public boolean isEnabled(){ return true; }
	
	public static ItemMechanics getInstance(){
		return (ItemMechanics) TerraCraft.getMechanic("ItemMechanics");
	}
	
	// Mechanic Variables
	
	private UUID uuid;
	private DatabaseMechanics databaseMechanics;
	
	private Dao<ItemComponentData, Integer> itemComponentDataDao;
	private Dao<ItemBase, Integer> itemBaseDao;
	private Dao<ItemInstance, Integer> itemInstanceDao;
	
	// Mechanic Methods
	
	public UUID getAttributeUUID() {
		return uuid;
	}
	
	public Dao<ItemComponentData, Integer> getItemComponentDao(){
		return itemComponentDataDao;
	}
	
	public Dao<ItemBase, Integer> getItemBaseDao(){
		return itemBaseDao;
	}
	
	public Dao<ItemInstance, Integer> getItemInstanceDao(){
		return itemInstanceDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		uuid = AttributeUtil.computeUUID("TerraGamingNetwork-TerraCraft");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		itemComponentDataDao = (Dao<ItemComponentData, Integer>) databaseMechanics.getDao(ItemComponentData.class);
		try {
			itemComponentDataDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		itemBaseDao = (Dao<ItemBase, Integer>) databaseMechanics.getDao(ItemBase.class);
		itemInstanceDao = (Dao<ItemInstance, Integer>) databaseMechanics.getDao(ItemInstance.class);
	
		try {
			itemBaseDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		new ItemComponentRegistry().init();
		new ItemInstanceRegistry().init();
		
		ItemComponentRegistry.registerComponents(itemComponentDataDao);
		
		try {
			itemBaseDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
