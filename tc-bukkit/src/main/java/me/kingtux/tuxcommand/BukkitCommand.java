package me.kingtux.tuxcommand;

import me.kingtux.tuxcommand.common.CommandExecutor;
import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.TuxCommand;

public class BukkitCommand implements CommandObject {
    @Override
    public CommandExecutor getExecutor() {
        return null;
    }

    @Override
    public TuxCommand getTuxCommand() {
        return null;
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
