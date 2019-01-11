package me.kingtux.tuxcommand.tabcompleter;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.TuxCommand;

public interface TabExecutor {
    Object execute(TuxCommand tuxCommand, Object[] args) throws CommandException;

}
