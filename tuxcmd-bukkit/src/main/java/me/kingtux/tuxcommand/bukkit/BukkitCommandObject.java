package me.kingtux.tuxcommand.bukkit;

import me.kingtux.tuxcommand.common.CommandExecutor;
import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.TuxCommand;

public class BukkitCommandObject implements CommandObject {
    private CommandExecutor executor;
    private TuxCommand tuxCommand;
    private Object[] args;

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public TuxCommand getTuxCommand() {
        return tuxCommand;
    }

    public void setTuxCommand(TuxCommand tuxCommand) {
        this.tuxCommand = tuxCommand;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
