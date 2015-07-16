package uk.co.terragaming.code.terracraft.mechanics.NPCMechanics;

import java.sql.SQLException;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;

import com.j256.ormlite.dao.Dao;

public class NPCMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static NPCMechanics getInstance() {
		return (NPCMechanics) TerraCraft.getMechanic("NPCMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private NPCRegistry registry;
	private Dao<NPCZone, Integer> zoneDao;
	
	// Mechanic Methods
	
	public static boolean isNPC(Entity entity){
		return entity.hasMetadata("NPC");
	}
	
	public Dao<NPCZone, Integer> getZoneDao(){
		return zoneDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	
	@Override
	public void Initialize() {
		registry = new NPCRegistry();
		NPC npc = registry.createNPC("Notch");
		npc.spawn(new Location(Bukkit.getWorld("TerraCraft_old"), 4000, 44, 3725));
		npc.setProtected(false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void PostInitialize() throws TerraException {
		databaseMechanics = DatabaseMechanics.getInstance();
		zoneDao = (Dao<NPCZone, Integer>) databaseMechanics.getDao(NPCZone.class);
		try {
			zoneDao.setObjectCache(true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TerraException();	
		}
	}
	
	@Override
	public void PreDenitialize() {
		
	}
	
	@Override
	public void Denitialize() {
		if (registry != null) registry.destroyAll();
	}
	
	@Override
	public void PostDenitialize() {
		
	}
	
}
