package uk.co.terragaming.code.terracraft;



public class TestMechanics implements Mechanic{

	public boolean isEnabled() 	{ return false; }
	public boolean isCore() 	{ return false; }
	
	public static TestMechanics getInstance(){
		return (TestMechanics) TerraCraft.getMechanic("TestMechanics");
	}

	// Mechanic Variables
	
	
	// Mechanic Methods
	
	
	// Mechanic Events
	
	@Override
	public void PreInitialize() {
		
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
