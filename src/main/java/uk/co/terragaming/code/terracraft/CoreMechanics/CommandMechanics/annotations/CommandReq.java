package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CommandReqs.class)
public @interface CommandReq {
	String[] value();
}
