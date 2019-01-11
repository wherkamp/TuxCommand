package me.kingtux.tuxcommand;

@FunctionalInterface
public interface MissingPermissionHandler {

    public void handle(BukkitCommandObject commandObject, BukkitArgumentSet t, String missingPermission);
}
