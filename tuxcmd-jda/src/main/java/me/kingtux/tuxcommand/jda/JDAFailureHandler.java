package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.FailureHandler;

@FunctionalInterface
public interface JDAFailureHandler extends FailureHandler<JDACommand> {
    void handle(CommandException exception, JDACommand jdaCommand);
}
