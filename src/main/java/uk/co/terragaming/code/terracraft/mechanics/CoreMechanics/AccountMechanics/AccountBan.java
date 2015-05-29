package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.enums.BanType;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bans")
public class AccountBan {
	
	@DatabaseField(generatedId = true, columnName = "banId")
	private Integer id;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "accountId")
	private Account account;
	
	@DatabaseField(canBeNull = false)
	private Integer serviceId;
	
	@DatabaseField(canBeNull = false)
	private BanType type;
	
	@DatabaseField(canBeNull = false)
	private String reason;
	
	@DatabaseField(canBeNull = false)
	private Integer repLost;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime dateIssued;
	
	@DatabaseField(canBeNull = false, columnName = "bannedUntil", persisterClass = DateTimePersister.class)
	private DateTime dateExpires;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "issuedById")
	private Account issuer;
	
	@DatabaseField(canBeNull = false)
	private Boolean active;
	
	// Getters
	
	public Integer getId() {
		return id;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public Integer getServiceId() {
		return serviceId;
	}
	
	public BanType getType() {
		return type;
	}
	
	public String getReason() {
		return reason;
	}
	
	public Integer getRepLost() {
		return repLost;
	}
	
	public DateTime getDateIssued() {
		return dateIssued;
	}
	
	public DateTime getDateExpires() {
		return dateExpires;
	}
	
	public Account getIssuer() {
		return issuer;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public Boolean isActive() {
		return active;
	}
	
	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	
	public void setType(BanType type) {
		this.type = type;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setRepLost(Integer repLost) {
		this.repLost = repLost;
	}
	
	public void setDateIssued(DateTime dateIssued) {
		this.dateIssued = dateIssued;
	}
	
	public void setDateExpires(DateTime dateExpires) {
		this.dateExpires = dateExpires;
	}
	
	public void setIssuer(Account issuer) {
		this.issuer = issuer;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	// Init
	
	public AccountBan() {}
	
	// Override Methods
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		return id.equals(((AccountBan) other).getId());
	}
	
	@Override
	public String toString() {
		return "AccountBan[ID: " + getId() + ", Account: " + getAccount().toString() + "]";
	}
	
}
