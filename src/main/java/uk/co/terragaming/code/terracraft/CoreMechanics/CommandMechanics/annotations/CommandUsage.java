package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandUsage {
	String value();
}
