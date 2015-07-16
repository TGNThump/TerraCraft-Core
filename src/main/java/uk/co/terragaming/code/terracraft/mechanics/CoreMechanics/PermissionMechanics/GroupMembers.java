package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groupMembers")
public class GroupMembers {
	
	@DatabaseField(generatedId = true, columnName = "id")
	private Integer id;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "accountId")
	private Account account;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "groupId")
	private Group group;

	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public Account getAccount() {
		return account;
	}

	
	public void setAccount(Account account) {
		this.account = account;
	}

	
	public Group getGroup() {
		return group;
	}

	
	public void setGroup(Group group) {
		this.group = group;
	}
	
}
