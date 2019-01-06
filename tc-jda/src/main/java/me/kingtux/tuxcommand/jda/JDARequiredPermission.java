package me.kingtux.tuxcommand.jda;


import net.dv8tion.jda.core.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
public @interface JDARequiredPermission {
    Permission permission();
}
