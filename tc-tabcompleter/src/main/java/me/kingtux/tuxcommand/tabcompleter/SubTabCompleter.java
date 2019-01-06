package me.kingtux.tuxcommand.tabcompleter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubTabCompleter {
    /**
     * So if you go to SubCommand and do @SubCommand(..., id="ID")
     * That id is what you put here!
     * @return the id for the SubCommand
     */
    String subID();
}
