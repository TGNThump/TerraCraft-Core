package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.CallbackMethod;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class Callback {
	
	private Method method;
	private Object object;
	private Object[] args;
	
	public Callback(String methodName, Object scope, Object... args) {
		Class<?> c = scope.getClass();
		Method[] methods = c.getDeclaredMethods();
		
		this.object = scope;
		this.args = args;
		
		for (Method method : methods) {
			if (!method.isAnnotationPresent(CallbackMethod.class)) {
				continue;
			}
			if (method.getName() != methodName) {
				continue;
			}
			
			this.method = method;
			return;
		}
		
		TerraLogger.error("Callback Method not Found: " + methodName + " in " + c.getSimpleName());
	}
	
	public void call() {
		if (method == null) return;
		try {
			method.invoke(object, args);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
}
