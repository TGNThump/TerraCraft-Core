package uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class AccountMechanics implements Mechanic{
	
	private static AccountMechanics instance;
	private AccountRegistry accountRegistry;
	
	public AccountMechanics(){
		instance = this;
		TerraLogger.info("  AccountMechanics Initialized");
	}
	
	public static AccountMechanics getInstance(){
		return instance;
	}
	
	
	public void PreInitialize() {
		accountRegistry = new AccountRegistry();
	}

	public void Initialize() {
		// TODO Auto-generated method stub
		
	}

	public void PostInitialize() {
		// TODO Auto-generated method stub
		
	}

	public void Deinitialize() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isEnabled() {
		return true;
	}
	
	
	public AccountRegistry getRegistry(){
		return accountRegistry;
	}
}