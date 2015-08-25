package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.Assert;


public class AccountProperty extends Property<Account>{
	
	private Account value;
	
	@Override
	public Account get() {
		return value;
	}

	@Override
	public void set(Account value) {
		this.value = value;
	}

	@Override
	public Object encode() {
		if (value == null) return null;
		return value.getId();
	}

	@Override
	public void decode(Object value) {
		this.value = null;
		if (value == null) return;
		Assert.ofType(value, Integer.class);
		Integer id = (Integer) value;
		
		if (AccountRegistry.hasAccount(id)){
			this.value = AccountRegistry.getAccount(id);
			return;
		}
		
		try {
			this.value = AccountMechanics.getInstance().getAccountsDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
}
