package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Exceptions.InvalidDatabaseCredentialsException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class DatabaseMechanics {
	
	public static void Initialize(){
		TerraLogger.info("  DatabaseMechanics Initialized");
		
		TerraCraft.Server().getScheduler().scheduleSyncDelayedTask(TerraCraft.Plugin(), new Runnable() {
			  public void run() {
				  try {
					new Database();
					TerraCraft.onDatabaseConnectionEstablished();
				} catch (ClassNotFoundException | SQLException | InvalidDatabaseCredentialsException e) {
					TerraLogger.blank();
					TerraLogger.error("Could not Initialize Database Connection...");
					e.printStackTrace();
					TerraLogger.blank();
					TerraLogger.error("Server Shutting Down...");
					TerraCraft.Server().shutdown();
				}
			  }
			}, 1L);
	}
	
	public static void Denitialize(){
		Database.getInstance().getConnectionPool().shutdown();
		TerraLogger.info("Database Connection Shutdown");
	}
}
