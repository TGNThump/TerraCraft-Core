package uk.co.terragaming.code.terracraft;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class MechanicLoader {
	private HashMap<String, Mechanic> mechanics = new HashMap<String, Mechanic>();
	
	public void constructMechanics(){
		
		TerraLogger.info("CoreMechanics Initialized");
		
		Reflections reflections = new Reflections("uk.co.terragaming.code.terracraft.CoreMechanics");    
		Set<Class<? extends Mechanic>> classes = reflections.getSubTypesOf(Mechanic.class);
		
		for(Class<? extends Mechanic> mechanicClass : classes){
			try {
				Mechanic mechanic = mechanicClass.newInstance();
				if (mechanic.isEnabled() && mechanic.isCore()){
					String className = mechanicClass.getName();
					className = className.substring(className.lastIndexOf(".") + 1);
					mechanics.put(className, mechanic);
					TerraLogger.info("  " + className + " Initialized");
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		reflections = new Reflections("uk.co.terragaming.code.terracraft");    
		classes = reflections.getSubTypesOf(Mechanic.class);
		
		for(Class<? extends Mechanic> mechanicClass : classes){
			try {
				Mechanic mechanic = mechanicClass.newInstance();
				if (mechanic.isEnabled() && (!mechanic.isCore())){
					String className = mechanicClass.getName();
					className = className.substring(className.lastIndexOf(".") + 1);
					mechanics.put(className, mechanic);
					TerraLogger.info("" + className + " Initialized");
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean hasMechanic(String mechanicName){
		return mechanics.containsKey(mechanicName);
	}
	
	public Mechanic getMechanic(String mechanicName){
		return mechanics.get(mechanicName);
	}
	
	public void preInitMechanics(){
		for(Mechanic mechanic : mechanics.values()){
			mechanic.PreInitialize();
		}
	}
	
	public void initMechanics(){
		for(Mechanic mechanic : mechanics.values()){
			mechanic.Initialize();
		}
	}
	
	public void postInitMechanics(){
		for(Mechanic mechanic : mechanics.values()){
			mechanic.PostInitialize();
		}
	}
	
	public void preDenitializeMechanics(){
		for(Mechanic mechanic : mechanics.values()){
			mechanic.PreDenitialize();
		}
	}
	
	public void denitializeMechanics(){
		for(Mechanic mechanic : mechanics.values()){
			mechanic.Denitialize();
		}
	}
	
	public void postDenitializeMechanics(){
		for(Mechanic mechanic : mechanics.values()){
			mechanic.PostDenitialize();
		}
	}
}