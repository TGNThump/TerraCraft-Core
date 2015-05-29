package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.Callback;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class CallBack {
	
	private Method method;
	private Object object;
	private Object[] args;
	
	public CallBack(String methodName, Object object, Object... args) {
		Method[] methods = object.getClass().getMethods();
		
		this.object = object;
		this.args = args;
		
		for (Method method : methods) {
			if (!method.isAnnotationPresent(Callback.class)) {
				continue;
			}
			if (method.getName() != methodName) {
				continue;
			}
			
			this.method = method;
			return;
		}
		
		TerraLogger.error("Callback Method not Found: " + methodName + " in " + object.getClass().getSimpleName());
	}
	
	public void invoke() {
		try {
			method.invoke(object, args);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
}
