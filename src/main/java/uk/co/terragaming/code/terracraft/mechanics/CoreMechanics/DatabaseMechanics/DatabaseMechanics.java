package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.exceptions.DatabaseException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@MechanicParent("CoreMechanics")
public class DatabaseMechanics implements Mechanic {
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static DatabaseMechanics getInstance() {
		return (DatabaseMechanics) TerraCraft.getMechanic("CoreMechanics.DatabaseMechanics");
	}
	
	// Mechanic Variables
	
	private Database database;
	private HashMap<Class<?>, Dao<?, ?>> dataAccessObjects = new HashMap<>();
	
	// Mechanic Methods
	
	public Connection getConnection() throws SQLException {
		return database.getConnection();
	}
	
	public Dao<?, ?> getDao(Class<?> cl) {
		if (dataAccessObjects.containsKey(cl))
			return dataAccessObjects.get(cl);
		try {
			Dao<?, ?> dao = DaoManager.createDao(database.getConnectionSource(), cl);
			dataAccessObjects.put(cl, dao);
			return dao;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void printResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		
		rs.beforeFirst();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				String columnValue = rs.getString(i);
				TerraLogger.debug(rsmd.getColumnName(i) + ": " + columnValue);
			}
			TerraLogger.blank();
		}
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		try {
			database = new Database();
		} catch (ClassNotFoundException | DatabaseException | SQLException e) {
			TerraLogger.blank();
			TerraLogger.error("Could not Initialize Database Connection...");
			e.printStackTrace();
			TerraLogger.blank();
			TerraLogger.error("Server Shutting Down...");
			TerraCraft.server.shutdown();
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
		database.ShutDown();
		database = null;
		dataAccessObjects = null;
		TerraLogger.blank();
		TerraLogger.info("Database Connection Shutdown.");
		TerraLogger.blank();
	}
	
}
