package me.kingtux.tuxcommand.common;

public interface FailureHandler<CO extends CommandObject> {

    void handle(CommandException ce, CO commandObject);
}
