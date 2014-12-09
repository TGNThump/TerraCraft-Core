package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Exceptions.InvalidDatabaseCredentialsException;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class Database {

	private final BoneCP connectionPool;
	private static Database instance;
	
	 public Database() throws ClassNotFoundException, SQLException, InvalidDatabaseCredentialsException
	    {
		 	instance = this;
		 	
	        Class.forName("com.mysql.jdbc.Driver");
	        
	        Properties credentials = DatabaseCredentials.getCredentials();
	        
	        // setup the connection pool
	        BoneCPConfig boneConfig = new BoneCPConfig();
	        boneConfig.setJdbcUrl("jdbc:mysql://" + credentials.getProperty("hostname") + "/" + credentials.getProperty("database")); // jdbc url specific to your database
	        boneConfig.setUsername(credentials.getProperty("username"));
	        boneConfig.setPassword(credentials.getProperty("password"));
	        boneConfig.setMinConnectionsPerPartition(5);
	        boneConfig.setMaxConnectionsPerPartition(10);
	        boneConfig.setPartitionCount(1);
	        this.connectionPool = new BoneCP(boneConfig); // setup the connection pool
	    }

	    protected void shutDown() {
	        this.connectionPool.shutdown();
	    }

	    public Connection getConnection() throws SQLException
	    {
	        return this.connectionPool.getConnection();
	    }

	    public BoneCP getConnectionPool()
	    {
	        return connectionPool;
	    }

		public static Database getInstance() {
			return instance;
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
		
}
