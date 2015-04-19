package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Repeatable;
import java.lang.annotation.RetentionPolicy;

import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.ArgType;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CommandArgs.class)
public @interface CommandArg {
	ArgType value();
}
