package uk.co.terragaming.TerraCore.Mechanics.Database;

import java.sql.Connection;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.Foundation.Mechanic;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCraft.TerraCraft;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseMechanics extends Mechanic{
	
	@Inject TerraLogger logger;
	private Database database;
	
	@Provides @Singleton Database database(){
		return database;
	}
	
	@Provides Connection provideConnection(Database database){
		return database.getConnection();
	}
	
	@Override
	public void preInit(){
		try {
			database = new Database();
			database.getConnection();
			database.getConnectionPool();
			database.getConnectionSource();
		} catch (Throwable e) {
			logger.blank();
			logger.error("Could not Initialize Database Connection...");
			e.printStackTrace();
			logger.blank();
			logger.error("Server Shutting Down...");
			TerraCraft.server.shutdown();
		}
	}
	
	@Override
	public void postDeinit() {
		database.ShutDown();
		database = null;
		logger.blank();
		logger.info("Database Connection Shutdown.");
		logger.blank();
	}
	
}
