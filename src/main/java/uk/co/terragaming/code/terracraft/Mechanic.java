package uk.co.terragaming.code.terracraft;

import uk.co.terragaming.code.terracraft.exceptions.TerraException;

public interface Mechanic {
	
	public boolean isEnabled();
	
	public void PreInitialize() throws TerraException;
	
	public void Initialize() throws TerraException;
	
	public void PostInitialize() throws TerraException;
	
	public void PreDenitialize() throws TerraException;
	
	public void Denitialize() throws TerraException;
	
	public void PostDenitialize() throws TerraException;
}