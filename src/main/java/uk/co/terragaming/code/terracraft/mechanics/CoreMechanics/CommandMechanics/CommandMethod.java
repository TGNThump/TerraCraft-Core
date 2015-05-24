package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;

import com.google.common.collect.Lists;

public class CommandMethod implements Comparator<Method>{

	public static List<Method> getMethodsInClass(Object handler){
		List<Method> methods = Lists.newArrayList(handler.getClass().getMethods());
		Collections.sort(methods, new CommandMethod());
		return methods;
	}
	
	// m1 < m2 => -1
	// m1 = m2 => 0
	// m1 > m2 => +1
	@Override
	public int compare(final Method m1, final Method m2) {
		if ((!isCommand(m1)) || (!isCommand(m2))) return 0;
		
		Integer m1Length = (hasParent(m1) ? getParentString(m1).split(" ").length : 0);
		Integer m2Length = (hasParent(m2) ? getParentString(m2).split(" ").length : 0);
		
		if (m1Length < m2Length){
			return -1;
		} else if (m1Length > m2Length){
			return 1;
		} else {
			return 0;
		}
	}
	
	public static boolean isCommand(Method method){
		return method.isAnnotationPresent(Command.class);
	}
	
	public static String getCommandName(Method method){
		Command commandAnnotation = method.getAnnotation(Command.class);
		return commandAnnotation.value()[0];
	}
	
	public static List<String> getCommandAliases(Method method){
		Command commandAnnotation = method.getAnnotation(Command.class);
		List<String> aliases = Lists.newArrayList(commandAnnotation.value());
		aliases.remove(0);
		return aliases;
	}
	
	public static boolean hasParent(Method method){
		return method.isAnnotationPresent(CommandParent.class);
	}
	
	public static Optional<uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.Command> getParent(Method method){
		if (!hasParent(method)) return Optional.empty();
		String[] args = getParentString(method).split(" ");
		uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.Command command = CommandRegistry.getCommand(args);
		if (command == null){ return Optional.empty(); }
		return Optional.of(command);
	}
	
	public static boolean hasDescription(Method method){
		return method.isAnnotationPresent(CommandDescription.class);
	}
	
	public static boolean hasUsage(Method method){
		return method.isAnnotationPresent(CommandUsage.class);
	}
	
	public static String getParentString(Method method){
		return method.getAnnotation(CommandParent.class).value();
	}
	
	public static String getDescription(Method method){
		return method.getAnnotation(CommandDescription.class).value();
	}
	
	public static String getUsage(Method method){
		return method.getAnnotation(CommandUsage.class).value();
	}
}
