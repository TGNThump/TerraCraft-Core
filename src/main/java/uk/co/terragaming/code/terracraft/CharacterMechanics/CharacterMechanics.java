package uk.co.terragaming.code.terracraft.CharacterMechanics;

import uk.co.terragaming.code.terracraft.Mechanic;
import uk.co.terragaming.code.terracraft.TerraCraft;

public class CharacterMechanics implements Mechanic{

	public boolean isEnabled() 	{ return true; }
	public boolean isCore() 	{ return false; }
	
	public static CharacterMechanics getInstance(){
		return (CharacterMechanics) TerraCraft.getMechanic("CharacterMechanics");
	}
	public CharacterMechanics(){ Construct();}

	// Mechanic Variables
	
	
	// Mechanic Methods
	private void Construct(){
		TerraCraft.Server().getPluginManager().registerEvents(new LoginListener(), TerraCraft.Plugin());
	}
	
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