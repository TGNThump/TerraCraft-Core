package uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Database;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class PermissionMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static PermissionMechanics getInstance(){
		return (PermissionMechanics) TerraCraft.getMechanic("PermissionMechanics");
	}

	// Mechanic Variables
	private HashMap<Integer, PermissionGroup> groups = new HashMap<Integer, PermissionGroup>();

	// Mechanic Methods
	public HashMap<Integer, PermissionGroup> getGroupsHashMap(){
		return groups;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
	}

	@Override
	public void Initialize() {
		try {
			Connection connection = Database.getInstance().getConnection();
			
			PreparedStatement query = connection.prepareStatement("SELECT * FROM groups", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			query.executeQuery();
			ResultSet results = query.getResultSet();
			results.beforeFirst();
			
			while(results.next()){
				groups.put(results.getInt("groupId"), new PermissionGroup(results.getInt("groupId"), results.getString("name")) );
			}
			
			PreparedStatement query1 = connection.prepareStatement("SELECT * FROM permissionAssignment a JOIN permissions p ON p.permissionId = a.permissionId", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			query1.executeQuery();
			ResultSet results1 = query1.getResultSet();
			results1.beforeFirst();
			
			while(results1.next()){
				groups.get(results1.getInt("groupId")).setPermissionSetring(results1.getString("permission"), results1.getString("permissionLevel"));
			}
			
			connection.close();

		} catch (Exception e){
			TerraLogger.error("Cannot retrieve Permission Groups from Database");
			e.printStackTrace();
		}
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
		groups.clear();
	}
}