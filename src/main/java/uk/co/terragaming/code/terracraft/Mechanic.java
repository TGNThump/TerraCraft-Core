package uk.co.terragaming.code.terracraft;

public interface Mechanic {
	public boolean isEnabled();
	
	public void PreInitialize();
	public void Initialize();
	public void PostInitialize();
	
	public void Deinitialize();
}
