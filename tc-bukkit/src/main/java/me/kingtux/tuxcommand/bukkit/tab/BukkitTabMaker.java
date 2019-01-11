package me.kingtux.tuxcommand.bukkit.tab;

import me.kingtux.tuxcommand.BukkitArgumentSet;
import me.kingtux.tuxcommand.BukkitCommandManager;
import me.kingtux.tuxcommand.BukkitCommandObject;
import me.kingtux.tuxcommand.BukkitRequiredPermission;
import me.kingtux.tuxcommand.bukkit.tab.objects.BukkitTabObject;
import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.tabcompleter.TabMaker;
import me.kingtux.tuxcommand.tabcompleter.TabObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BukkitTabMaker implements TabMaker<BukkitCommandManager, BukkitArgumentSet> {
    @Override
    public TabObject buildTab(Method methodToInvoke, TuxCommand tuxCommand, BukkitCommandManager commandManager, BukkitArgumentSet t) {
        if (TuxUtils.contains(methodToInvoke.getParameterTypes(), Player.class)) {
            if (!(t.getCommandSender() instanceof Player)) {
                t.getCommandSender().sendMessage(ChatColor.translateAlternateColorCodes('&', commandManager.getMustBeAPlayer()));
                return null;
            }
        }
        BukkitTabObject commandObject = new BukkitTabObject();
        commandObject.setArgs(createArguments(methodToInvoke, t.toArray()));
        commandObject.setTc(tuxCommand);
        commandObject.setExecutor((TuxCommand tuxCommand1, Object[] args1) -> {
            try {
                return methodToInvoke.invoke(tuxCommand1, args1);
            } catch (InvocationTargetException e) {
                throw new CommandException(e.getCause());
            } catch (Throwable e) {
                throw new CommandException(e);
            }
        });

        boolean hasPermission = true;
        BukkitRequiredPermission classPermission = tuxCommand.getClass().getAnnotation(BukkitRequiredPermission.class);
        if (classPermission != null) {
            if (!t.getCommandSender().hasPermission(classPermission.permission())) {
                hasPermission = false;
            }
        }
        //Method Check
        if (hasPermission) {
            BukkitRequiredPermission methodPermission = methodToInvoke.getAnnotation(BukkitRequiredPermission.class);
            if (methodPermission != null) {
                if (!t.getCommandSender().hasPermission(classPermission.permission())) {
                    hasPermission = false;
                }
            }
        }
        if (!hasPermission) {
            return null;
        }
        return commandObject;
    }
}
