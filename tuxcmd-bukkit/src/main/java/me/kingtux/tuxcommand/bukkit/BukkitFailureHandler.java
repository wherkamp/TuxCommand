package me.kingtux.tuxcommand.bukkit;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.FailureHandler;

public interface BukkitFailureHandler extends FailureHandler<BukkitCommandObject> {
    @Override
    public void handle(CommandException ce, BukkitCommandObject commandObject);
}
