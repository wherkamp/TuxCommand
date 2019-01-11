package me.kingtux.tuxcommand.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Sub Command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    String subCommand();

    /**
     * the aliases for the commands
     *
     * @return aliases for the sub command
     */
    String[] alias() default "";

    /**
     * The Format on how to use this sub command
     *
     * @return the format
     */
    String format() default "";

    /**
     * The description
     *
     * @return the SubCommand description
     */
    String description() default "";

}
