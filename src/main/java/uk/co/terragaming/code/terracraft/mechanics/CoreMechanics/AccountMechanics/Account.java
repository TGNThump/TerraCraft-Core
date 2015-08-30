package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics;

import java.util.Date;

import org.bukkit.entity.Player;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.enums.Gender;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.BanMechanics.AccountBan;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Account {
	
	@DatabaseField(generatedId = true, columnName = "accountId")
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String terraTag;
	
	@DatabaseField(canBeNull = false)
	private String firstName;
	
	@DatabaseField(canBeNull = false)
	private String lastName;
	
	@DatabaseField(canBeNull = false)
	private Integer rep;
	
	@DatabaseField(canBeNull = false)
	private String country;
	
	@DatabaseField(canBeNull = false)
	private Gender gender;
	
	@DatabaseField(canBeNull = false)
	private Date dateOfBirth;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime signUpDate;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime lastLogDate;
	
	// TODO: Database Languages
	private Language language = Language.ENGLISH;
	
	@ForeignCollectionField(eager = false)
	private ForeignCollection<AccountSession> sessions;
	
	@ForeignCollectionField(eager = true, foreignFieldName = "account")
	private ForeignCollection<AccountBan> bans;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<Character> characters;
	
	// Non Database Fields
	
	private Player player;
	private Character activeCharacter;
	private AccountSession activeSession;
	private Channel activeChannel;
	
	// Getters
	
	public Integer getId() {
		return id;
	}
	
	public String getTerraTag() {
		return terraTag;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Integer getRep() {
		return rep;
	}
	
	public String getCountry() {
		return country;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public DateTime getSignUpDate() {
		return signUpDate;
	}
	
	public DateTime getLastLogDate() {
		return lastLogDate;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public ForeignCollection<AccountSession> getSessions() {
		return sessions;
	}
	
	public ForeignCollection<AccountBan> getBans() {
		return bans;
	}
	
	public ForeignCollection<Character> getCharacters() {
		return characters;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public AccountSession getActiveSession() {
		return activeSession;
	}
	
	public Character getActiveCharacter() {
		return activeCharacter;
	}
	
	public Channel getActiveChannel() {
		return activeChannel;
	}
	
	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setTerraTag(String terraTag) {
		this.terraTag = terraTag;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setRep(Integer rep) {
		this.rep = rep;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public void setSignUpDate(DateTime signUpDate) {
		this.signUpDate = signUpDate;
	}
	
	public void setLastLogDate(DateTime lastLogDate) {
		this.lastLogDate = lastLogDate;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setActiveSession(AccountSession activeSession) {
		this.activeSession = activeSession;
	}
	
	public void setActiveCharacter(Character activeCharacter) {
		this.activeCharacter = activeCharacter;
	}

	public void setActiveChannel(Channel activeChannel) {
		this.activeChannel = activeChannel;
	}
	
	// Init
	
	public Account() {}
	
	// Override Methods
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		return id.equals(((Account) other).getId());
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName() + "[<h>" + getId() + "<r>]";
	}
}
