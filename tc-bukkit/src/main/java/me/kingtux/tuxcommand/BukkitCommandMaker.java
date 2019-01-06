package me.kingtux.tuxcommand;

import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;
import me.kingtux.tuxcommand.common.internals.CommandMaker;

import java.lang.reflect.Method;

public class BukkitCommandMaker implements CommandMaker<BukkitCommandManager, BukkitArgumentSet> {

    @Override
    public CommandObject buildCommand(Method methodToInvoke, TuxCommand tuxCommand, BukkitCommandManager commandManager, BukkitArgumentSet t) {
        return null;
    }
}
