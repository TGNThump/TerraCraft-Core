package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;

public abstract class Command {
	
	private Object handler;
	private Method method;
	private List<Command> children = Lists.newArrayList();
	private CommandParameter[] params;
	
	public Command(Integer paramLength) {
		params = new CommandParameter[paramLength];
	}
	
	public Object getHandler() {
		return handler;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public List<Command> getChildren() {
		return children;
	}
	
	public CommandParameter[] getParams() {
		return params;
	}
	
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public void addChild(Command command) {
		children.add(command);
	}
	
	public void addParam(CommandParameter param, Integer index) {
		params[index] = param;
	}
	
	public abstract String getName();
	
	public abstract String getPath();
	
	public abstract List<String> getAliases();
	
	public abstract String getDescription();
	
	public abstract String getUsage();
	
	public abstract Command getParent();
	
	public abstract void setAliases(List<String> aliases);
	
	public abstract void setDescription(String description);
	
	public abstract void setUsage(String usage);
	
	public abstract void setParent(Command parent);
}
