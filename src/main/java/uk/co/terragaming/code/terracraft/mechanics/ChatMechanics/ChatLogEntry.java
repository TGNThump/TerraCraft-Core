package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics;

import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcChatLog")
public class ChatLogEntry {
	
	@DatabaseField(generatedId = true, columnName = "chatLogId")
	private Integer id;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "accountId")
	private Account account;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "characterId")
	private Character character;
	
	@DatabaseField(canBeNull = false)
	private String channel;
	
	@DatabaseField(canBeNull = false)
	private String message;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime timestamp;
	
	@DatabaseField(canBeNull = false)
	private String server;
	
	public Integer getId() {
		return id;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public String getMessage() {
		return message;
	}
	
	public DateTime getTimestamp() {
		return timestamp;
	}
	
	public String getServer() {
		return server;
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
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
}
