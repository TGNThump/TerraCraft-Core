package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;


public class AccountSerializer extends Serializer<Account>{

	@Override
	public Account StringToObject(String value) {
		Integer accId;
		try {
			accId = new IntegerSerializer().StringToObject(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (accId == null) return null;
		try {
			if (AccountRegistry.hasAccount(accId)) return AccountRegistry.getAccount(accId);
			return AccountMechanics.getInstance().getAccountsDao().queryForId(accId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String ObjectToString(Object object) {
		if (object == null) return null;
		if (!(object instanceof Account)) return null;
		try {
			return (new IntegerSerializer().ObjectToString(((Account) object).getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
