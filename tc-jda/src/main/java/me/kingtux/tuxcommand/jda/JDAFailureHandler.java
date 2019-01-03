package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.CommandException;

@FunctionalInterface
public interface JDAFailureHandler {
    void onFail(CommandException exception, JDACommand jdaCommand);
}
