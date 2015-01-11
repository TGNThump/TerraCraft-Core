package uk.co.terragaming.code.terracraft.CharacterMechanics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Database;

public class CharacterMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return false; }
	
	public static CharacterMechanics getInstance(){
		return (CharacterMechanics) TerraCraft.getMechanic("CharacterMechanics");
	}
	public CharacterMechanics(){ Construct();}

	// Mechanic Variables
	private HashMap<Integer, Character> chars1 = new HashMap<Integer, Character>();
	private HashMap<Integer, HashSet<Integer>> chars2 = new HashMap<Integer, HashSet<Integer>>();
	
	// Mechanic Methods
	private void Construct(){
		TerraCraft.Server().getPluginManager().registerEvents(new LoginListener(), TerraCraft.Plugin());
	}
	
	public void downloadCharacters(Account account) throws SQLException {
		String SQL = "SELECT * FROM tcCharacters WHERE accountId = ?";
		
		Connection connection = Database.getInstance().getConnection();
		
		PreparedStatement query = connection.prepareStatement(SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		query.setInt(1, account.getId());
		query.executeQuery();
		
		connection.close();
		
		ResultSet results = query.getResultSet();
		Integer count = 0;
		
		try {
			results.last();
		    count = results.getRow();
		    results.first();
		} catch(Exception ex) {
		    count = 0;
		}
		
		if (count == 0){
			HashSet<Integer> set = new HashSet<Integer>();
			chars2.put(account.getId(), set);
		} else {
			results.beforeFirst();
			
			while(results.next()){
				Character character = new Character();
				character.setAccountId(account.getId());
				character.setId(results.getInt("characterId"));
				character.setRaceId(results.getInt("raceId"));
				character.setName(results.getString("name"));
				
				character.setAttribute(CharacterAttribute.STRENGTH, results.getInt("attrStrength"));
				character.setAttribute(CharacterAttribute.AGILITY, results.getInt("attrAgility"));
				character.setAttribute(CharacterAttribute.STAMINA, results.getInt("attrStamina"));
				character.setAttribute(CharacterAttribute.SPIRIT, results.getInt("attrSpirit"));
				character.setAttribute(CharacterAttribute.RESISTANCE, results.getInt("attrResistance"));
				character.setAttribute(CharacterAttribute.INTELLECT, results.getInt("attrIntellect"));
				character.setAttribute(CharacterAttribute.VITALITY, results.getInt("attrVitality"));
				
				character.setMaxHitpoints(results.getInt("maxHitpoints"));
				character.setCurHitpoints(results.getInt("curHitpoints"));
				
				character.setMaxHunger(results.getInt("maxHunger"));
				character.setCurHunger(results.getInt("curHunger"));
				
				character.setMaxMana(results.getInt("maxMana"));
				character.setCurMana(results.getInt("curMana"));
				
				character.setCurLevel(results.getInt("curLevel"));
				character.setCurExp(results.getInt("curExp"));
				character.setMoney(results.getInt("money"));
				character.setBankMoney(results.getInt("bankMoney"));
				character.setLocation(new Location(
					Bukkit.getWorld(results.getString("locWorld")),
					results.getInt("locX"),
					results.getInt("locY"),
					results.getInt("locZ"),
					results.getFloat("locYaw"),
					results.getFloat("locPitch")
				));
				
				character.setLastLogDate(DateTime.now());
				character.setCreateDate(new DateTime(results.getDate("createDate")));
				
				character.setDescription(results.getString("description"));
				character.setDescription_ooc(results.getString("description_ooc"));
				character.setNotes(results.getString("notes"));
				
				chars1.put(character.getId(), character);
				if(chars2.containsKey(account.getId())){
					chars2.get(account.getId()).add(character.getId());
				}
				HashSet<Integer> set = new HashSet<Integer>();
				set.add(character.getId());
				chars2.put(account.getId(), set);
			}
		}
	}
	
	public Character[] getCharactersByAccount(Account account){
		HashSet<Integer> charIds = chars2.get(account.getId());
		Character[] chars = new Character[charIds.size()];
		Integer i = 0;
		for(Integer charId : charIds){
			chars[i] = chars1.get(charId);
			i++;
		}
		
		return chars;
	}
	
	public Character getCharacter(Integer id){
		return chars1.get(id);
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {

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
		
	}
}