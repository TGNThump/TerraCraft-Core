package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Repeatable;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CommandArgs.class)
public @interface CommandArg {
	String[] value();
}
