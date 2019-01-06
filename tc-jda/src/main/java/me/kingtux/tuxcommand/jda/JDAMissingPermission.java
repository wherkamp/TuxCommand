package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;
import net.dv8tion.jda.core.Permission;
@FunctionalInterface
public interface JDAMissingPermission {
    void handle(JDACommand commandObject, JDAArgumentSet argumentSet, Permission permissionNeeded);
}
