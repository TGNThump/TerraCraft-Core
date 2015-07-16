package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;

import com.j256.ormlite.dao.Dao;

@MechanicRequires("CoreMechanics.DatabaseMechanics")
public class FealtyGroupMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() { return true; }
	
	public static FealtyGroupMechanics getInstance() {
		return (FealtyGroupMechanics) TerraCraft.getMechanic("FealtyGroupMechanics");
	}
	
	// Mechanic Variables
	
	private DatabaseMechanics databaseMechanics;
	private Dao<FealtyGroup, Integer> fealtyGroupsDao;
	
	// Mechanic Methods
	
	public Dao<FealtyGroup, Integer> getFealtyGroupDao(){
		return fealtyGroupsDao;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void Initialize() {
		databaseMechanics = DatabaseMechanics.getInstance();
		fealtyGroupsDao = (Dao<FealtyGroup, Integer>) databaseMechanics.getDao(FealtyGroup.class);
		new FealtyGroupRegistry().init();
		new FealtyGroupManager().init();
	}
	
	@Override
	public void PostInitialize() {
		
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
