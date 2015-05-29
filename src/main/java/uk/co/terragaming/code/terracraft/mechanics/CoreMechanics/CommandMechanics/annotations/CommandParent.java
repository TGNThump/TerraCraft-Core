package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandParent {
	
	String value();
}
