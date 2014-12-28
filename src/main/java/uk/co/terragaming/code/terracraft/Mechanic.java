package uk.co.terragaming.code.terracraft;

public interface Mechanic {
	public boolean isEnabled();
	public boolean isCore();
	
	public void PreInitialize();
	public void Initialize();
	public void PostInitialize();
	
	public void PreDenitialize();
	public void Denitialize();
	public void PostDenitialize();
}
