package uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics;

import java.util.Properties;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Exceptions.InvalidCredentialsException;

public class DatabaseCredentials {
	static Properties getCredentials() throws InvalidCredentialsException{
		
		try{
		
		Properties properties = new Properties();
		properties.put("username", TerraCraft.Config().get("TerraCraft.Database.Username"));
		properties.put("password", TerraCraft.Config().get("TerraCraft.Database.Password"));
		
		properties.put("hostname", TerraCraft.Config().get("TerraCraft.Database.Hostname"));
		properties.put("port", TerraCraft.Config().get("TerraCraft.Database.Port"));
		
		properties.put("database", TerraCraft.Config().get("TerraCraft.Database.Database"));
		
		return properties;
		
		} catch(Exception e){
			throw new InvalidCredentialsException();
		}
	}
}
