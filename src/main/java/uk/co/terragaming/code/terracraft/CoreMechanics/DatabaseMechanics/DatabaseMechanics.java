package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Exceptions.InvalidDatabaseCredentialsException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class DatabaseMechanics implements Mechanic{

	public DatabaseMechanics(){
		TerraLogger.info("  DatabaseMechanics Initialized");
	}
	
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

	public void Initialize() {
		// TODO Auto-generated method stub
		
	}

	public void PostInitialize() {
		// TODO Auto-generated method stub
		
	}

	public void Deinitialize() {
		Database.getInstance().getConnectionPool().shutdown();
		TerraLogger.info("Database Connection Shutdown");
	}
	
	public boolean isEnabled() {
		return true;
	}
}