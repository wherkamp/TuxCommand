package me.kingtux.tuxcommand;

import me.kingtux.simpleannotation.AnnotationWriter;
import me.kingtux.tuxcommand.bukkit.tab.BukkitTabMaker;
import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.MyCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.annotations.Command;
import me.kingtux.tuxcommand.common.internals.InternalCommand;
import me.kingtux.tuxcommand.tabcompleter.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.List;

public class BukkitCommandManager implements CommandManager<BukkitFailureHandler, BukkitCommandMaker>, TabManager<BukkitTabMaker> {
    private String mustBeAPlayer = "You must be a player";
    private BukkitFailureHandler failureHandler;
    private MissingPermissionHandler mph;
    private List<InternalCommand> registeredCommands;
    private BukkitCommandMaker bcm;
    private BukkitTabMaker btm;
    private Plugin plugin;

    public BukkitCommandManager(Plugin plugin) {
        if (plugin == null || Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()) != null)
            throw new IllegalArgumentException("Dont lie to me Blart - Pahud");
        this.plugin = plugin;
        if (getCommandMap() == null) {
            throw new IncompatibleServerException("TuxCommand is incompatible with " + Bukkit.getServer().getName());
        }
    }

    @Override
    public void register(TuxCommand tuxCommand, MyCommand rules) {
        if (getInternalCommand(tuxCommand) != null) {
            try {
                throw new IllegalAccessException("Hey, You have already registered that command!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        if (rules != null) AnnotationWriter.writeToAnnotation(tuxCommand.getClass(), Command.class, rules);
        BukkitCommandExecutor bce = new BukkitCommandExecutor(tuxCommand, this);
        registeredCommands.add(bce);
        getCommandMap().register(plugin.getName(), bce);
    }

    private CommandMap getCommandMap() {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            return (CommandMap) commandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public InternalCommand getInternalCommand(TuxCommand t) {
        for (InternalCommand internalCommand : registeredCommands) {
            if (internalCommand.getTuxCommand() == t) {
                return internalCommand;
            }
        }
        return null;
    }

    @Override
    public BukkitFailureHandler getFailureHandler() {
        return failureHandler;
    }

    @Override
    public void setFailureHandler(BukkitFailureHandler bukkitFailureHandler) {
        if (bukkitFailureHandler == null) {
            return;
        }
        failureHandler = bukkitFailureHandler;
    }

    @Override
    public BukkitCommandMaker getCommandMaker() {
        return bcm;
    }

    @Override
    public List<TuxCommand> getRegisteredCommands() {
        return null;
    }

    public String getMustBeAPlayer() {
        return mustBeAPlayer;
    }

    public void setMustBeAPlayer(String mustBeAPlayer) {
        this.mustBeAPlayer = mustBeAPlayer;
    }

    public MissingPermissionHandler getPermission() {
        return mph;
    }

    @Override
    public BukkitTabMaker getTabMaker() {
        return btm;
    }
}

