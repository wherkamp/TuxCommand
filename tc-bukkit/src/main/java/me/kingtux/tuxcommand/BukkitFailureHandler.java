package me.kingtux.tuxcommand;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.FailureHandler;

public interface BukkitFailureHandler extends FailureHandler<BukkitCommand> {
    @Override
    public void handle(CommandException ce, BukkitCommand commandObject);
}
