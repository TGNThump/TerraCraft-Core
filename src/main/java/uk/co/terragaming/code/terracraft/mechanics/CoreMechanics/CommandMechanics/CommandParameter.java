package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg.AR;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CommandParameter {
	
	private String paramName;
	private Class<?> type;
	private String defaultValue;
	private boolean isOptional = false;
	private boolean isTag = false;
	private boolean isNamed = false; // Reflection cannot get names when not
										// compiled with java 8
	private boolean isSpecial = false; // Special parameters are assigned
										// automatically, no arguments required
	private AR<?> argReader;
	
	public String getName() {
		return paramName;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public boolean isOptional() {
		return isOptional;
	}
	
	public boolean isTag() {
		return isTag;
	}
	
	public boolean isNamed() {
		return isNamed;
	}
	
	public boolean isSpecial() {
		return isSpecial;
	}
	
	public AR<?> getArgReader() {
		return argReader;
	}
	
	public CommandParameter() {}
	
	public void setName(String name) {
		paramName = name;
		isNamed = true;
	}
	
	public void setType(Class<?> type) throws TerraException {
		this.type = type;
		if (isSpecial())
			return;
		try {
			Class<?> arClass = Class.forName("uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg.AR" + Txt.upperCaseFirst(type.getSimpleName()));
			argReader = (AR<?>) arClass.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new TerraException("<b>Could not find ArgReader for type '" + type.getSimpleName() + "'. [Error Type: " + e.getClass().getSimpleName() + "]");
		}
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public void setOptional(boolean optional) {
		isOptional = optional;
	}
	
	public void setTag(boolean tag) {
		isTag = tag;
	}
	
	public void setSpecial(boolean special) {
		isSpecial = special;
	}
}
