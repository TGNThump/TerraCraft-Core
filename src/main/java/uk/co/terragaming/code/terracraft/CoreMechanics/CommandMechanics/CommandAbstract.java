package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public abstract class CommandAbstract {

	//TODO: Handle CommandArgs and CommandReqs
	
	private Object handler;
	private Method method;
	private HashMap<String, CommandAbstract> children = new HashMap<String, CommandAbstract>(); // Includes Aliases
	
	public Object getHandler(){
		return handler;
	}
	
	public void setHandler(Object handler){
		this.handler = handler;
	}
	
	public Method getMethod(){
		return method;
	}
	
	public void setMethod(Method method){
		this.method = method;
	}
	
	public HashMap<String, CommandAbstract> getChildren(){
		return children;
	}
	
	public void addChild(CommandAbstract command){
		addChild(command.getName(), command);
	}
	
	public void addChild(String name, CommandAbstract command){
		children.put(name, command);
		if (command.getAliases().isEmpty()){ return; }
		for(String alias : command.getAliases()){
			children.put(alias, command);
		}
	}
	
	public abstract String getName();
	public abstract String getPath();
	public abstract List<String> getAliases();
	public abstract String getDescription();
	public abstract String getUsage();
	public abstract CommandAbstract getParent();
	
	public abstract void setAliases(List<String> aliases);
	public abstract void setDescription(String description);
	public abstract void setUsage(String usage);
	public abstract void setParent(CommandAbstract parent);
	
	
}
