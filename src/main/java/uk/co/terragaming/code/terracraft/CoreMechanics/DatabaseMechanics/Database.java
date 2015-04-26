package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Exceptions.InvalidCredentialsException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class Database {

	private final BoneCP connectionPool;
	
	public Database() throws ClassNotFoundException, SQLException, InvalidCredentialsException {
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Properties credentials = DatabaseCredentials.getCredentials();
		
        BoneCPConfig boneConfig = new BoneCPConfig();
        boneConfig.setJdbcUrl("jdbc:mysql://" + credentials.getProperty("hostname") + "/" + credentials.getProperty("database"));
        boneConfig.setUsername(credentials.getProperty("username"));
        boneConfig.setPassword(credentials.getProperty("password"));
        boneConfig.setMinConnectionsPerPartition(1);
        boneConfig.setMaxConnectionsPerPartition(10);
        boneConfig.setPartitionCount(1);
        this.connectionPool = new BoneCP(boneConfig);
	}
	
	protected void ShutDown(){
		this.connectionPool.shutdown();
	}
	
	protected Connection getConnection() throws SQLException{
		return this.connectionPool.getConnection();
	}
	
	protected BoneCP getConnectionPool(){
		return connectionPool;
	}
}
