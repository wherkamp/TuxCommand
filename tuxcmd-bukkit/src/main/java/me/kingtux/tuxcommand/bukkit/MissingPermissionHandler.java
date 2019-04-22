package me.kingtux.tuxcommand.bukkit;

@FunctionalInterface
public interface MissingPermissionHandler {

    public void handle(BukkitCommandObject commandObject, BukkitArgumentSet t, String missingPermission);
}
