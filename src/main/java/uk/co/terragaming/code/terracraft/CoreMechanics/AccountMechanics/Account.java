package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Exceptions.AccountBannedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.Exceptions.AccountNotLinkedException;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.Database;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.PermissionGroup;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.PermissionGroupKey;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.PermissionMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.permLevel;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.permType;
import uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics.PlayerMessages;

public class Account {
	private Integer id;
	private Integer sessionId = 0;
	private String terraTag;
	private String firstName;
	private String lastName;
	private Integer rep;
	private String country;
	
	private DateTime dateOfBirth;
	private DateTime signUpDate;
	private DateTime lastLogDate;
	
	private UUID playerUUID;
	private InetAddress ipAddress;
	
	private HashMap<Integer, AccountBan> bans = new HashMap<Integer, AccountBan>();
	private HashMap<PermissionGroupKey, PermissionGroup> groups = new HashMap<PermissionGroupKey, PermissionGroup>();
	
	private Integer curCharacterId;

	public Account(UUID uniqueId, InetAddress ipAddress) {
		this.playerUUID = uniqueId;
		this.ipAddress = ipAddress;
	}
	
	
	
	public void downloadData() throws AccountNotLinkedException, SQLException, AccountBannedException{
		String UUID = getPlayerUUID().toString();
		String SQL = "SELECT a.accountId, a.terraTag, a.firstName, a.lastName, a.rep, a.country, a.dateOfBirth, a.signUpDate, a.lastLogDate, s.sessionId, s.ipAddress, s.userAgent, s.firstUsed, s.lastUsed, b.banId, b.type, b.reason, b.rep_lost, b.dateIssued, b.bannedUntil, b.issuedById, s.enabled, g.groupId FROM accounts a INNER JOIN accountSessions s ON a.accountId = s.accountId LEFT JOIN bans b ON a.accountId = b.accountId AND (b.serviceId = '3' OR b.type = 'GLOBAL') AND b.active = '1' LEFT JOIN groupMembers g ON a.accountId = g.accountId WHERE s.serviceId = '3' AND s.userAgent = ?";
	
		Connection connection = Database.getInstance().getConnection();
		
		PreparedStatement query = connection.prepareStatement(SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		query.setString(1, "Minecraft (" + UUID + ")");
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
			throw new AccountNotLinkedException();
		} else {
			results.beforeFirst();
			
			while(results.next()){
				if (results.getString("ipAddress").equals(getIpAddress().toString().substring(1))){
					setSessionId(results.getInt("sessionId"));
					if (!results.getBoolean("enabled")){
						throw new AccountBannedException(PlayerMessages.get("account_access_revoked"));
					}
				}
				
				if (results.getInt("banId") > 0 && !bans.containsKey(results.getInt("banId"))){
					AccountBan ban = new AccountBan();
					
					ban.setBanId(results.getInt("banId"));
					ban.setAccountId(results.getInt("accountId"));
					ban.setType(results.getString("type"));
					ban.setReason(results.getString("reason"));
					ban.setRep_lost(results.getInt("rep_lost"));
					ban.setDateIssued(results.getDate("dateIssued"));
					ban.setBannedUntil(results.getDate("bannedUntil"));
					ban.setIssuedById(results.getInt("issuedById"));
					ban.setActive(true);
					
					bans.put(ban.getAccountId(), ban);
				}
				
				if(results.getInt("groupId") > 0 && !groups.containsKey(results.getInt("groupId"))){
					PermissionGroup group = PermissionMechanics.getInstance().getGroupsHashMap().get(results.getInt("groupId"));
					groups.put(new PermissionGroupKey(group.getGroupId(), group.getGroupName()), group);
				}
				

			}
			results.first();
			
			setId(results.getInt("accountId"));
			setTerraTag(results.getString("terraTag"));
			setFirstName(results.getString("firstName"));
			setLastName(results.getString("lastName"));
			setRep(results.getInt("rep"));
			setCountry(results.getString("country"));
			setDateOfBirth(results.getDate("dateOfBirth"));
			setSignUpDate(results.getDate("signUpDate"));
			
			if (groups.size() == 0){
				PermissionGroup group = PermissionMechanics.getInstance().getGroupsHashMap().get(6);
				groups.put(new PermissionGroupKey(group.getGroupId(), group.getGroupName()), group);
			}
		}
		
	}
	
	public void uploadData() throws SQLException{

	}
	
	public void updateSession(){
		updateSession(true);
	}
	
	public void updateSession(Boolean active){
		try{
			Connection connection = Database.getInstance().getConnection();
			if (getSessionId() > 0){
				String SQL = "UPDATE accountSessions SET lastUsed = now(), active = ? WHERE sessionId = ?";
				
				PreparedStatement query = connection.prepareStatement(SQL);
				query.setBoolean(1, active);
				query.setInt(2, getSessionId());
				query.execute();
				
				connection.close();
			} else {
				String SQL = "INSERT INTO accountSessions (accountId, serviceId, ipAddress, userAgent, enabled, active) VALUES (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement query = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				query.setInt(1, getId());
				query.setInt(2, 3);
				query.setString(3, getIpAddress().toString().substring(1));
				query.setString(4, "Minecraft (" + getPlayerUUID().toString() + ")");
				query.setBoolean(5, true);
				query.setBoolean(6, active);
				query.executeUpdate();
				
				ResultSet rs = query.getGeneratedKeys();
				rs.first();
				setSessionId(rs.getInt(1));
			}
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void onLogin() throws AccountNotLinkedException, AccountBannedException, SQLException {
		downloadData();
		
		for (Integer banId : bans.keySet()){
			AccountBan ban = bans.get(banId);
			switch(ban.getType()){
				case "TEMP":
					DateTime now = DateTime.now();
					DateTime bannedUntil = ban.getBannedUntil();
					if (now.isBefore(bannedUntil)){
						Period period = new Period(now, bannedUntil);
						PeriodFormatterBuilder Formater = new PeriodFormatterBuilder().appendDays().appendSeparator(" Days, ").appendHours().appendSeparator(" Hours, ").appendMinutes().appendSeparator(" Minutes and ").appendSeconds();
						
						throw new AccountBannedException(ChatColor.RED + "You are temp-banned from TerraCraft for another " + Formater.toFormatter().print(period) + " Seconds.");
					}
					ban.setActive(false);
				case "PERM":
					throw new AccountBannedException(PlayerMessages.get("account_banned_perm"));
				case "GLOBAL":
					throw new AccountBannedException(PlayerMessages.get("account_banned_global"));
			}
		}
		
		updateSession(true);
	}
	
	public void onLogout() throws SQLException {
		uploadData();
		updateSession(false);
	}
	
	public PermissionGroup[] getGroupsAsArray(){
		try{
			PermissionGroup[] result = new PermissionGroup[groups.size()];
			Integer i = 0;
			for(PermissionGroupKey key : groups.keySet()){
				result[groups.size() - 1 - i] = groups.get(key);
				i++;
			}
			return result;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean hasPermission(String permission, permType permType, permLevel permLevel){
		for (PermissionGroup group : getGroupsAsArray()){
			if (group.hasPermission(permission, permType, permLevel)){
				return true;
			}
		}
		return false;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	public String getTerraTag() {
		return terraTag;
	}

	public void setTerraTag(String terraTag) {
		this.terraTag = terraTag;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getRep() {
		return rep;
	}

	public void setRep(Integer rep) {
		this.rep = rep;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public DateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(DateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public DateTime getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(DateTime signUpDate) {
		this.signUpDate = signUpDate;
	}
	
	private void setSignUpDate(Date date) {
		setSignUpDate(new DateTime(date));
		
	}

	private void setDateOfBirth(Date date) {
		setDateOfBirth(new DateTime(date));
		
	}
	
	public void setLastLogDate(Date date) {
		setLastLogDate(new DateTime(date));
	}
	

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public DateTime getLastLogDate() {
		return lastLogDate;
	}

	public void setLastLogDate(DateTime lastLogDate) {
		this.lastLogDate = lastLogDate;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public HashMap<PermissionGroupKey, PermissionGroup> getGroups() {
		return groups;
	}



	public Integer getCurCharacterId() {
		return curCharacterId;
	}



	public void setCurCharacterId(Integer curCharacterId) {
		this.curCharacterId = curCharacterId;
	}
}
