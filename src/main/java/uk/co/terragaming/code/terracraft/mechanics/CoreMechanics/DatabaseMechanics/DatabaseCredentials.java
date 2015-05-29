package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics;

import java.util.Properties;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.exceptions.DatabaseException;

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
