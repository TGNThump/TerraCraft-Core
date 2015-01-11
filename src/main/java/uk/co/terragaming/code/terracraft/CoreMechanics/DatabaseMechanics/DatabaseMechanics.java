package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.exceptions.InvalidCredentialsException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class DatabaseMechanics implements Mechanic{
	
	public boolean isEnabled()	{ return true; }
	public boolean isCore()		{ return true; }
	
	public static DatabaseMechanics getInstance(){
		return (DatabaseMechanics) TerraCraft.getMechanic("DatabaseMechanics");
	}
	
	// Mechanics Variables
	private Database database;
	
	// Mechanics Methods
	public Connection getConnection() throws SQLException{
		return database.getConnection();
	}
	
	public static void printResultSet(ResultSet rs) throws SQLException{
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
	
	// Mechanics Events
	
	@Override
	public void PreInitialize(){
		try{
			database = new Database();
		} catch (ClassNotFoundException | SQLException | InvalidCredentialsException e){
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
		database.ShutDown();
		TerraLogger.blank();
		TerraLogger.info("Database Connection Shutdown");
		TerraLogger.blank();
	}
}
