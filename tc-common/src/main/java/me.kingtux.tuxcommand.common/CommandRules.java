package me.kingtux.tuxcommand.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CommandRules the rules for the command
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandRules {

    String description();

    String[] aliases();

    String format();
}
