package uk.co.terragaming.code.terracraft;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.reflections.Reflections;

import uk.co.terragaming.code.terracraft.annotations.MechanicParent;
import uk.co.terragaming.code.terracraft.annotations.MechanicRequires;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

import com.google.common.collect.Lists;

public class MechanicLoader implements Comparator<Class<? extends Mechanic>> {
	
	private HashMap<String, Mechanic> mechanics = new HashMap<String, Mechanic>();
	
	public void constructMechanics() {
		List<Class<? extends Mechanic>> classes = getClasses();
		
		for (Class<? extends Mechanic> mechanicClass : classes) {
			try {
				Mechanic mechanic = mechanicClass.newInstance();
				
				if (mechanic.isEnabled()) {
					String className = mechanicClass.getName();
					className = className.substring(className.lastIndexOf(".") + 1);
					
					String parentPath = null;
					
					if (hasParent(mechanicClass)) {
						parentPath = getParentString(mechanicClass);
						
						if (!mechanics.keySet().contains(parentPath)) {
							TerraLogger.error("Failed to enable " + mechanicClass + ", because parent " + parentPath + " was not found.");
							mechanic = null;
							continue;
						}
					}
					
					String classPath = parentPath == null ? className : parentPath + "." + className;
					mechanics.put(classPath, mechanic);
					
				} else {
					mechanic = null;
					continue;
				}
				
			} catch (InstantiationException | IllegalAccessException e) {
				TerraLogger.error("Failed to Construct Mechanics... Server Shutting Down...");
				e.printStackTrace();
				TerraCraft.server.shutdown();
			}
		}
		
		for (Entry<String, Mechanic> entry : mechanics.entrySet()) {
			String classPath = entry.getKey();
			Mechanic mechanic = entry.getValue();
			
			Class<? extends Mechanic> c = mechanic.getClass();
			if (hasRequirements(c)) {
				List<String> requiredPaths = Lists.newArrayList(getRequirements(c));
				
				if (!mechanics.keySet().containsAll(requiredPaths)) {
					TerraLogger.error("Failed to enable " + classPath + ", because a required mechanic was not found.");
					mechanics.remove(classPath);
					mechanic = null;
					break;
				}
			}
		}
		
		List<String> paths = Lists.newArrayList(mechanics.keySet());
		
		Collections.sort(paths);
		
		for (String path : paths) {
			String mechanicName = path;
			
			Integer tabs = 0;
			
			if (path.contains(".")) {
				mechanicName = path.substring(path.lastIndexOf(".") + 1);
				String[] parts = path.split("\\.");
				tabs = parts.length;
			}
			
			TerraLogger.info(TerraLogger.tools.repeat("  ", tabs) + mechanicName + " Initialized");
		}
		TerraLogger.blank();
	}
	
	private static List<Class<? extends Mechanic>> getClasses() {
		Reflections reflections = new Reflections("uk.co.terragaming.code.terracraft.mechanics");
		List<Class<? extends Mechanic>> classes = Lists.newArrayList(reflections.getSubTypesOf(Mechanic.class));
		Collections.sort(classes, new MechanicLoader());
		return classes;
	}
	
	@Override
	public int compare(Class<? extends Mechanic> c1, Class<? extends Mechanic> c2) {
		Integer c1Length = hasParent(c1) ? getParentString(c1).split("\\.").length : 0;
		Integer c2Length = hasParent(c2) ? getParentString(c2).split("\\.").length : 0;
		
		if (c1Length < c2Length)
			return -1;
		else if (c1Length > c2Length)
			return 1;
		else
			return 0;
	}
	
	private boolean hasParent(Class<? extends Mechanic> c) {
		return c.isAnnotationPresent(MechanicParent.class);
	}
	
	private String getParentString(Class<? extends Mechanic> c) {
		return c.getAnnotation(MechanicParent.class).value();
	}
	
	private boolean hasRequirements(Class<? extends Mechanic> c) {
		return c.isAnnotationPresent(MechanicRequires.class);
	}
	
	private String[] getRequirements(Class<? extends Mechanic> c) {
		MechanicRequires[] annotations = c.getAnnotationsByType(MechanicRequires.class);
		String[] ret = new String[annotations.length];
		
		int i = 0;
		for (MechanicRequires annotation : annotations) {
			ret[i] = annotation.value();
			i++;
		}
		
		return ret;
	}
	
	public boolean hasMechanic(String mechanicName) {
		return mechanics.containsKey(mechanicName);
	}
	
	public Mechanic getMechanic(String mechanicName) {
		return mechanics.get(mechanicName);
	}
	
	public void preInitMechanics() {
		for (Mechanic mechanic : mechanics.values()) {
			mechanic.PreInitialize();
		}
	}
	
	public void initMechanics() {
		for (Mechanic mechanic : mechanics.values()) {
			mechanic.Initialize();
		}
	}
	
	public void postInitMechanics() {
		for (Mechanic mechanic : mechanics.values()) {
			mechanic.PostInitialize();
		}
	}
	
	public void preDenitializeMechanics() {
		for (Mechanic mechanic : mechanics.values()) {
			mechanic.PreDenitialize();
		}
	}
	
	public void denitializeMechanics() {
		for (Mechanic mechanic : mechanics.values()) {
			mechanic.Denitialize();
		}
	}
	
	public void postDenitializeMechanics() {
		for (Mechanic mechanic : mechanics.values()) {
			mechanic.PostDenitialize();
		}
	}
}