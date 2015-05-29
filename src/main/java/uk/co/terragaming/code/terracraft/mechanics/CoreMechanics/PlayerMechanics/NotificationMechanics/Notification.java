package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NotificationMechanics;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcNotifications")
public class Notification {
	
	@DatabaseField(generatedId = true, columnName = "notificationId")
	private Integer id;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "accountId")
	private Account account;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "characterId")
	private Character character;
	
	@DatabaseField(canBeNull = false)
	private String message;
	
	public Integer getId() {
		return id;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
