package uk.co.terragaming.TerraCore.Foundation;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.reflections.Reflections;

import uk.co.terragaming.TerraCore.Plugin;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCore.Util.Text.Text;
import uk.co.terragaming.TerraCraft.TerraCraft;

import com.google.common.collect.Lists;

public class MechanicLoader implements Comparator<Class<? extends Mechanic>>{

	public HashMap<String, Mechanic> mechanics = new HashMap<String, Mechanic>();
	private final TerraLogger logger;
	
	public MechanicLoader(TerraLogger _logger){
		logger = _logger;
	}
	
	public void constructAll(){
		List<Class<? extends Mechanic>> classes = getClasses();
		
		for(Class<? extends Mechanic> mClass : classes){
			try{
				Mechanic mechanic = mClass.newInstance();
				if (!mechanic.isEnabled()) continue;
				
				String className = mClass.getName();
				className = className.substring(className.lastIndexOf(".") + 1);
				
				String parentPath = null;
				
				if (hasParent(mClass)){
					parentPath = getParentString(mClass);
					
					if (!mechanics.keySet().contains(parentPath)){
						logger.error("Failed to enable " + mClass + ", because parent " + parentPath + " was not found.");
						continue;
					}
				}
				
				String classPath = parentPath == null ? className : parentPath + "." + className;
				mechanics.put(classPath, mechanic);
			} catch (Exception e){
				logger.error("Server encountered <h>%s<r> while loading <h>%s<r>", e.getClass().getSimpleName(), mClass.getSimpleName());
				e.printStackTrace();
				TerraCraft.server.shutdown();
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
			
			logger.info(Text.repeat("  ", tabs) + mechanicName + " Initialized");
		}
		
		logger.blank();
	}
	
	// Init
	
	public void preInitAll(){
		Mechanic m = null;
		try{
			for(Mechanic mechanic : mechanics.values()){
				m = mechanic;
				m.preInit();
			}
		} catch (Exception e){
			if (m != null)
				logger.error("Server encountered <h>%s<r> while preInitializing <h>%s<r>", e.getClass().getSimpleName(), m.getClass().getSimpleName());
			e.printStackTrace();
			Plugin.server.shutdown();
		}
	}
	
	public void initAll(){
		Mechanic m = null;
		try{
			for(Mechanic mechanic : mechanics.values()){
				m = mechanic;
				m.init();
			}
		} catch (Exception e){
			if (m != null)
				logger.error("Server encountered <h>%s<r> while initializing <h>%s<r>", e.getClass().getSimpleName(), m.getClass().getSimpleName());
			e.printStackTrace();
			Plugin.server.shutdown();
		}
	}
	
	public void postInitAll(){
		Mechanic m = null;
		try{
			for(Mechanic mechanic : mechanics.values()){
				m = mechanic;
				m.postInit();
			}
		} catch (Exception e){
			if (m != null)
				logger.error("Server encountered <h>%s<r> while postInitializing <h>%s<r>", e.getClass().getSimpleName(), m.getClass().getSimpleName());
			e.printStackTrace();
			Plugin.server.shutdown();
		}
	}
	
	// Deinit
	
	public void preDeinitAll() {
		Mechanic m = null;
		try{
			for(Mechanic mechanic : mechanics.values()){
				m = mechanic;
				m.preDeinit();
			}
		} catch (Exception e){
			if (m != null)
				logger.error("Server encountered <h>%s<r> while preDeinitializing <h>%s<r>", e.getClass().getSimpleName(), m.getClass().getSimpleName());
			e.printStackTrace();
			Plugin.server.shutdown();
		}
	}
	
	public void deinitAll() {
		Mechanic m = null;
		try{
			for(Mechanic mechanic : mechanics.values()){
				m = mechanic;
				m.deinit();
			}
		} catch (Exception e){
			if (m != null)
				logger.error("Server encountered <h>%s<r> while deinitializing <h>%s<r>", e.getClass().getSimpleName(), m.getClass().getSimpleName());
			e.printStackTrace();
			Plugin.server.shutdown();
		}
	}
	
	public void postDeinitAll() {
		Mechanic m = null;
		try{
			for(Mechanic mechanic : mechanics.values()){
				m = mechanic;
				m.postDeinit();
			}
		} catch (Exception e){
			if (m != null)
				logger.error("Server encountered <h>%s<r> while postDeinitializing <h>%s<r>", e.getClass().getSimpleName(), m.getClass().getSimpleName());
			e.printStackTrace();
			Plugin.server.shutdown();
		}
	}
	
	// Util
	
	private List<Class<? extends Mechanic>> getClasses() {
		Reflections reflections = new Reflections("uk.co.terragaming");
		List<Class<? extends Mechanic>> classes = Lists.newArrayList(reflections.getSubTypesOf(Mechanic.class));
		Collections.sort(classes, new MechanicLoader(logger));
		return classes;
	}
	
	// Comparator
	
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
}