package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import uk.co.terragaming.code.terracraft.exceptions.DatabaseException;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class Database {

	private final BoneCP connectionPool;
	private final ConnectionSource connectionSource;
	
	public Database() throws ClassNotFoundException, SQLException, DatabaseException {
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Properties credentials = DatabaseCredentials.getCredentials();
		
        BoneCPConfig boneConfig = new BoneCPConfig();
        boneConfig.setJdbcUrl("jdbc:mysql://" + credentials.getProperty("hostname") + "/" + credentials.getProperty("database"));
        boneConfig.setUsername(credentials.getProperty("username"));
        boneConfig.setPassword(credentials.getProperty("password"));
        boneConfig.setMinConnectionsPerPartition(1);
        boneConfig.setMaxConnectionsPerPartition(10);
        boneConfig.setPartitionCount(1);
        BoneCPDataSource dataSource = new BoneCPDataSource(boneConfig);
        
        this.connectionPool = new BoneCP(boneConfig);
        this.connectionSource = new DataSourceConnectionSource(dataSource, boneConfig.getJdbcUrl());
	}
	
	protected void ShutDown(){
		this.connectionPool.shutdown();
		try {
			this.connectionSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected ConnectionSource getConnectionSource(){
		return this.connectionSource;
	}
	
	protected Connection getConnection() throws SQLException{
		return this.connectionPool.getConnection();
	}
	
	protected BoneCP getConnectionPool(){
		return connectionPool;
	}
}
