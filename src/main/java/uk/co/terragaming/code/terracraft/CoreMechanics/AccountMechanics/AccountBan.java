package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import java.sql.Date;

import org.joda.time.DateTime;

public class AccountBan {
	private Integer banId;
	private Integer accountId;
	private String type;
	private String reason;
	private Integer rep_lost;
	private DateTime dateIssued;
	private DateTime bannedUntil;
	private Integer issuedById;
	private Boolean active;
	
	
	
	public Integer getBanId() {
		return banId;
	}
	public void setBanId(Integer banId) {
		this.banId = banId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getRep_lost() {
		return rep_lost;
	}
	public void setRep_lost(Integer rep_lost) {
		this.rep_lost = rep_lost;
	}
	public DateTime getDateIssued() {
		return dateIssued;
	}
	public void setDateIssued(DateTime dateIssued) {
		this.dateIssued = dateIssued;
	}
	public DateTime getBannedUntil() {
		return bannedUntil;
	}
	public void setBannedUntil(DateTime bannedUntil) {
		this.bannedUntil = bannedUntil;
	}
	public Integer getIssuedById() {
		return issuedById;
	}
	public void setIssuedById(Integer issuedById) {
		this.issuedById = issuedById;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public void setDateIssued(Date date) {
		setDateIssued(new DateTime(date));
	}
	public void setBannedUntil(Date date) {
		setBannedUntil(new DateTime(date));
	}
}
