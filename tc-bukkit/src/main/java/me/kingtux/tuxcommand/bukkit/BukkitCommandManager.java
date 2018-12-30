package me.kingtux.tuxcommand.bukkit;

import me.kingtux.simpleannotation.AnnotationWriter;
import me.kingtux.tuxcommand.common.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;


public class BukkitCommandManager implements CommandManager {
    private Plugin plugin;

    public BukkitCommandManager(Plugin plugin) {
        this.plugin = plugin;
        if (getCommandMap() == null) {
            plugin.getLogger().severe("Sorry, your server does not support TuxCommand-Bukkit !");
        }
    }


    @Override
    public void register(TuxCommand tuxCommand, MyCommandRules rules) {

        if (rules != null) {
            if (rules.getCommandRules() == null) {
                rules.setCommandRules(tuxCommand.getClass().getAnnotation(CommandRules.class));
            }
            AnnotationWriter.writeToAnnotation(tuxCommand.getClass(), CommandRules.class, rules);
        }
        CommandMap commandMap = getCommandMap();
        commandMap.register(plugin.getName().toLowerCase(), new BukkitInternalCommand(tuxCommand));
    }

    @Override
    public InternalCommand getInternalCommand(TuxCommand t) {
        return (InternalCommand) getCommandMap().getCommand(t.getCommandRules().aliases()[0]);
    }

    public static CommandMap getCommandMap() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            return (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
