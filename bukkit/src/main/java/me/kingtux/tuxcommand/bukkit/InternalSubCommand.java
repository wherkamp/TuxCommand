package me.kingtux.tuxcommand.bukkit;

import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class InternalSubCommand {
    private final SubCommand annotation;
    private final TabCompleter tabCompleter;
    private final Method methodToInvoke, tabCompleterMethod;
    private final TuxCommand commandObject;

    protected InternalSubCommand(SubCommand annotation, TabCompleter tabCompleter, Method methodToInvoke, Method tabCompleterMethod, TuxCommand commandObject) {
        this.annotation = annotation;
        this.tabCompleter = tabCompleter;
        this.methodToInvoke = methodToInvoke;
        this.tabCompleterMethod = tabCompleterMethod;
        this.commandObject = commandObject;
    }

     void invoke(String[] args, CommandSender sender, String commandUsed) {
        try {
            Object[] stuff = getParameters(args, methodToInvoke, commandObject, sender, commandUsed);
            if (stuff == null) {
                return;
            }
            methodToInvoke.invoke(commandObject, stuff);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

     List<String> invokeTab(String[] args, CommandSender sender, String commandUsed) {
        try {
            Object[] stuff = getParameters(args, methodToInvoke, commandObject, sender, commandUsed);
            if (stuff == null) {
                return Collections.emptyList();
            }
            return (List<String>) tabCompleterMethod.invoke(commandObject, stuff);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SubCommand getAnnotation() {
        return annotation;
    }

    public Method getMethodToInvoke() {
        return methodToInvoke;
    }

    public TuxCommand getCommandObject() {
        return commandObject;
    }

    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    public Method getTabCompleterMethod() {
        return tabCompleterMethod;
    }
    public static Object[] getParameters(String[] args, Method command, TuxCommand ikeaCommand, CommandSender commandSender, String commandUsed) {
        Class<?>[] parameterTypes = command.getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (type == String[].class) {
                parameters[i] = args;
            } else if (type == String.class) {
                parameters[i] = commandUsed;
            } else if (type.isInstance(commandSender)) {
                if (type == Player.class && !(commandSender instanceof Player)) {
                    commandSender.sendMessage("");
                } else {
                    parameters[i] = commandSender;
                }
            }
        }
        return parameters;
    }
}
