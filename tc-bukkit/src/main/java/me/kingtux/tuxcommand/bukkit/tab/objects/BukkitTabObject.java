package me.kingtux.tuxcommand.bukkit.tab.objects;

import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.tabcompleter.TabExecutor;
import me.kingtux.tuxcommand.tabcompleter.TabObject;

public class BukkitTabObject implements TabObject {
    private TabExecutor executor;
    private TuxCommand tc;
    private Object[] args;

    public BukkitTabObject(TabExecutor executor, TuxCommand tc, Object[] args) {
        this.executor = executor;
        this.tc = tc;
        this.args = args;
    }

    public void setExecutor(TabExecutor executor) {
        this.executor = executor;
    }

    public void setTc(TuxCommand tc) {
        this.tc = tc;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public BukkitTabObject() {

    }

    @Override
    public TabExecutor getExecutor() {
        return executor;
    }

    @Override
    public TuxCommand getTuxCommand() {
        return tc;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}
