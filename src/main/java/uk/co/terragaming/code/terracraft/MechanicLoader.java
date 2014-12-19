package uk.co.terragaming.code.terracraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class MechanicLoader {
	private List<Mechanic> mechanics = new ArrayList<Mechanic>();
	
	public void constructMechanics(){
		Reflections reflections = new Reflections("uk.co.terragaming.code.terracraft");    
		Set<Class<? extends Mechanic>> classes = reflections.getSubTypesOf(Mechanic.class);
		
		for(Class<? extends Mechanic> mechanicClass : classes){
			try {
				Mechanic mechanic = mechanicClass.newInstance();
				if (mechanic.isEnabled()){
					mechanics.add(mechanic);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void preInitMechanics(){
		for(Mechanic mechanic : mechanics){
			mechanic.PreInitialize();
		}
	}
	
	public void initMechanics(){
		for(Mechanic mechanic : mechanics){
			mechanic.Initialize();
		}
	}
	
	public void postInitMechanics(){
		for(Mechanic mechanic : mechanics){
			mechanic.PostInitialize();
		}
	}
	
	public void deInitializeMechanics(){
		for(Mechanic mechanic : mechanics){
			mechanic.Deinitialize();
		}
	}
}
