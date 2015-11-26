package uk.co.terragaming.TerraCore.Mechanics.Database;

import java.util.Properties;

import uk.co.terragaming.TerraCore.Util.Exceptions.DatabaseException;
import uk.co.terragaming.TerraCraft.TerraCraft;

public class DatabaseCredentials {
	
	public static Properties getCredentials() throws DatabaseException {
		
		try {
			
			Properties properties = new Properties();
			properties.put("username", TerraCraft.plugin.getConfig().get("TerraCraft.Database.Username"));
			properties.put("password", TerraCraft.plugin.getConfig().get("TerraCraft.Database.Password"));
			
			properties.put("hostname", TerraCraft.plugin.getConfig().get("TerraCraft.Database.Hostname"));
			properties.put("port", TerraCraft.plugin.getConfig().get("TerraCraft.Database.Port"));
			
			properties.put("database", TerraCraft.plugin.getConfig().get("TerraCraft.Database.Database"));
			
			return properties;
			
		} catch (Exception e) {
			throw new DatabaseException();
		}
	}
}