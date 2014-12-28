package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Exceptions.InvalidDatabaseCredentialsException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class DatabaseMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static DatabaseMechanics getInstance(){
		return (DatabaseMechanics) TerraCraft.getMechanic("DatabaseMechanics");
	}

	// Mechanic Variables
	
	
	// Mechanic Methods
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		try {
			new Database();
		} catch (ClassNotFoundException | SQLException | InvalidDatabaseCredentialsException e) {
			TerraLogger.blank();
			TerraLogger.error("Could not Initialize Database Connection...");
			e.printStackTrace();
			TerraLogger.blank();
			TerraLogger.error("Server Shutting Down...");
			TerraCraft.Server().shutdown();
		}
	}

	@Override
	public void Initialize() {
		
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
		Database.getInstance().getConnectionPool().shutdown();
		TerraLogger.blank();
		TerraLogger.info("Database Connection Shutdown");
		TerraLogger.blank();
	}
}