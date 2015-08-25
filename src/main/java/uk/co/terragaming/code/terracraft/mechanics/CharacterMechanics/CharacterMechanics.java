package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.commands.CharacterCommands;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.commands.CharacterStaffCommands;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners.CharacterDeathMessages;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners.CharacterJoinQuitMessages;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners.LoginListener;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners.LogoutListener;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.listeners.ShiftClickListener;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.CommandRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;

import com.j256.ormlite.dao.Dao;

@MechanicRequires("CoreMechanics.DatabaseMechanics")
public class CharacterMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static CharacterMechanics getInstance() {
		return (CharacterMechanics) TerraCraft.getMechanic("CharacterMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private Dao<Character, Integer> charactersDao;
	
	// Mechanic Methods
	
	public Dao<Character, Integer> getCharacterDao() {
		return charactersDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		charactersDao = (Dao<Character, Integer>) databaseMechanics.getDao(Character.class);
		try {
			charactersDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		CharacterManager.init();
		TerraCraft.server.getPluginManager().registerEvents(new LoginListener(), TerraCraft.plugin);
		TerraCraft.server.getPluginManager().registerEvents(new LogoutListener(), TerraCraft.plugin);
		TerraCraft.server.getPluginManager().registerEvents(new ShiftClickListener(), TerraCraft.plugin);
		TerraCraft.server.getPluginManager().registerEvents(new CharacterDeathMessages(), TerraCraft.plugin);
		TerraCraft.server.getPluginManager().registerEvents(new CharacterJoinQuitMessages(), TerraCraft.plugin);
	}
	
	@Override
	public void PostInitialize() {
		CommandRegistry.registerCommands(TerraCraft.plugin, new CharacterStaffCommands());
		CommandRegistry.registerCommands(TerraCraft.plugin, new CharacterCommands());
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
