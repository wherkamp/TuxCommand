package me.kingtux.tuxcommand;

import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.MyCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.internals.InternalCommand;
import me.kingtux.tuxcommand.tabcompleter.TabHandler;

import java.util.List;

public class BukkitCommandManager implements CommandManager<BukkitFailureHandler, BukkitCommandMaker>, TabHandler {
    @Override
    public void register(TuxCommand tuxCommand, MyCommand rules) {

    }

    @Override
    public InternalCommand getInternalCommand(TuxCommand t) {
        return null;
    }

    @Override
    public BukkitFailureHandler getFailureHandler() {
        return null;
    }

    @Override
    public void setFailureHandler(BukkitFailureHandler bukkitFailureHandler) {

    }

    @Override
    public BukkitCommandMaker getCommandMaker() {
        return null;
    }

    @Override
    public List<TuxCommand> getRegisteredCommands() {
        return null;
    }

}
