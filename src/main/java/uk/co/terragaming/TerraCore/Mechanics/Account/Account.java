package uk.co.terragaming.TerraCore.Mechanics.Account;

import uk.co.terragaming.TerraCore.Foundation.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Account extends Model{
	
	@DatabaseField(generatedId = true)
	private Integer id;
	
	
}
