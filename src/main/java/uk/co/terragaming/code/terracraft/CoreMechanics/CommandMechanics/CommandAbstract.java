package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandAbstract {

	//TODO: Handle CommandArgs and CommandReqs
	
	private Object handler;
	private Method method;
	private List<CommandAbstract> subCommands = new ArrayList<CommandAbstract>(); // Includes Aliases
	
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
	
	public List<CommandAbstract> getSubCommands(){
		return subCommands;
	}
	
	public void addSubCommand(CommandAbstract command){
		subCommands.add(command);
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
