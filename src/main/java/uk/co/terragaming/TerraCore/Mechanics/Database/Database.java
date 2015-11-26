package uk.co.terragaming.TerraCore.Mechanics.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import uk.co.terragaming.TerraCore.Events.database.DatabaseFailEvent;
import uk.co.terragaming.TerraCore.Util.Facades.Events;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class Database {
	
	private final BoneCP connectionPool;
	private final ConnectionSource connectionSource;
	
	private HashMap<Class<?>, Dao<?, ?>> dataAccessObjects = new HashMap<>(); 

	public Database() throws Throwable {
		
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
		
		connectionPool = new BoneCP(boneConfig);
		
		connectionSource = new DataSourceConnectionSource(dataSource, boneConfig.getJdbcUrl());
	}
	
	protected void ShutDown() {
		connectionPool.shutdown();
		try {
			connectionSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected ConnectionSource getConnectionSource() {
		return connectionSource;
	}
	
	protected Connection getConnection() {
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			DatabaseFailEvent event = new DatabaseFailEvent();
			Events.call(event);
			e.printStackTrace();
		}
		return null;
	}
	
	protected BoneCP getConnectionPool() {
		return connectionPool;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Dao<T, Integer> getDao(Class<T> cl) {
		if (dataAccessObjects.containsKey(cl))
			return (Dao<T, Integer>) dataAccessObjects.get(cl);
		try {
			Dao<?, ?> dao = DaoManager.createDao(getConnectionSource(), cl);
			dataAccessObjects.put(cl, dao);
			return (Dao<T, Integer>) dao;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
