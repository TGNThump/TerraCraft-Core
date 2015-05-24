package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.net.InetAddress;

import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accountSessions")
public class AccountSession {
	
	@DatabaseField(generatedId = true, columnName = "sessionId")
	private Integer id;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "accountId")
	private Account account;
	
	@DatabaseField(canBeNull = false)
	private Integer serviceId;
	
	@DatabaseField(canBeNull = false)
	private String ipAddress;
	
	@DatabaseField(canBeNull = true)
	private String userAgent;
	
	@DatabaseField(canBeNull = true)
	private String hash;
	
	@DatabaseField(canBeNull = true, persisterClass = DateTimePersister.class)
	private DateTime firstUsed;

	@DatabaseField(canBeNull = true, persisterClass = DateTimePersister.class)
	private DateTime lastUsed;
	
	@DatabaseField(canBeNull = false)
	private Boolean enabled;
	
	@DatabaseField(canBeNull = false)
	private Boolean active;
	
	// Getters
	
	public Integer getId(){ return id; }
	public Account getAccount(){ return account; }
	public Integer getServiceId(){ return serviceId; }
	public String getIpAddress(){ return ipAddress; }
	public String getUserAgent(){ return userAgent; }
	public String getHash(){ return hash; }
	public DateTime getFirstUsed(){ return firstUsed; }
	public DateTime getLastUsed(){ return lastUsed; }
	public Boolean getEnabled(){ return enabled; }
	public Boolean getActive(){ return active; }
	
	public Boolean isEnabled(){ return enabled; }
	public Boolean isActive(){ return active; }
	
	// Setters
	
	public void setId(Integer id){ this.id = id; }
	public void setAccount(Account account){ this.account = account; }
	public void setServiceId(Integer serviceId){ this.serviceId = serviceId; }
	public void setIpAddress(String ipAddress){ this.ipAddress = ipAddress; }
	public void setIpAddress(InetAddress ipAddress){ this.ipAddress = ipAddress.toString().substring(1); }
	public void setUserAgent(String userAgent){ this.userAgent = userAgent; }
	public void setHash(String hash){ this.hash = hash; }
	public void setFirstUsed(DateTime firstUsed){ this.firstUsed = firstUsed; }
	public void setLastUsed(DateTime lastUsed){ this.lastUsed = lastUsed; }
	public void setEnabled(Boolean enabled){ this.enabled = enabled; }
	public void setActive(Boolean active){ this.active = active; }
	
	// Init
	
	public AccountSession(){}
	
	// Override Methods
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null || other.getClass() != getClass()) return false;
		return id.equals(((AccountSession) other).getId());
	}
	
	@Override
	public String toString(){
		return "AccountSession[ID: " + getId() + ", Account: " + account.toString() + "]";
	}
	
}
