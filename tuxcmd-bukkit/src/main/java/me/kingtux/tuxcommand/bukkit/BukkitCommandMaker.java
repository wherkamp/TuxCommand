package me.kingtux.tuxcommand.bukkit;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.internals.CommandMaker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BukkitCommandMaker implements CommandMaker<BukkitCommandManager, BukkitArgumentSet> {

    @Override
    public CommandObject buildCommand(Method methodToInvoke, TuxCommand tuxCommand, BukkitCommandManager commandManager, BukkitArgumentSet t) {
        if (TuxUtils.contains(methodToInvoke.getParameterTypes(), Player.class)) {
            if (!(t.getCommandSender() instanceof Player)) {
                t.getCommandSender().sendMessage(ChatColor.translateAlternateColorCodes('&', commandManager.getMustBeAPlayer()));
                return null;
            }
        }
        BukkitCommandObject commandObject = new BukkitCommandObject();
        commandObject.setArgs(createArguments(methodToInvoke, t.toArray()));
        commandObject.setTuxCommand(tuxCommand);
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
        String missingPermission = "";
        BukkitRequiredPermission classPermission = tuxCommand.getClass().getAnnotation(BukkitRequiredPermission.class);
        if (classPermission != null) {
            if (!t.getCommandSender().hasPermission(classPermission.permission())) {
                hasPermission = false;
                missingPermission = classPermission.permission();
            }
        }
        //Method Check
        if (hasPermission) {
            BukkitRequiredPermission methodPermission = methodToInvoke.getAnnotation(BukkitRequiredPermission.class);
            if (methodPermission != null) {
                if (!t.getCommandSender().hasPermission(classPermission.permission())) {
                    hasPermission = false;
                    missingPermission = methodPermission.permission();
                }
            }
        }
        if (!hasPermission) {
            if (commandManager.getPermission() != null) {
                commandManager.getPermission().handle(commandObject, t, missingPermission);
            }
            return null;
        }
        return commandObject;
    }
}
