package me.kingtux.tuxcommand.common;

public interface CommandExecutor {
    Object execute(TuxCommand tuxCommand, Object[] args) throws CommandException;
}
