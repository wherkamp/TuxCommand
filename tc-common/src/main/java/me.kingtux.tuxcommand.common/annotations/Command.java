package me.kingtux.tuxcommand.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command the rules for the command
 *
 * @author KingTux
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * A Description of the command
     * @return the description
     */
    String description() default "";

    /**
     * What is used to identify the command
     * @return the command
     */
    String[] aliases();

    /**
     * THe Format
     * @return the format
     */
    String format() default "";
}
