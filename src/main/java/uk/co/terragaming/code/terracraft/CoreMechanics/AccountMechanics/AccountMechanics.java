package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class AccountMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return true; }
	
	public static AccountMechanics getInstance(){
		return (AccountMechanics) TerraCraft.getMechanic("AccountMechanics");
	}

	// Mechanic Variables
	private AccountRegistry accountRegistry;
	
	// Mechanic Methods
	
	public AccountRegistry getRegistry(){
		return accountRegistry;
	}
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		this.accountRegistry = new AccountRegistry();
	}

	@Override
	public void Initialize() {
		
	}

	@Override
	public void PostInitialize() {
		
	}

	@Override
	public void PreDenitialize() {
		
	}

	@Override
	public void Denitialize() {
		
	}

	@Override
	public void PostDenitialize() {
		
	}
}