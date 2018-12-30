package me.kingtux.tuxcommand.bukkit;


import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class InternalBaseCommand {
    private final BaseCommand annotation;
    private final Method methodToInvoke, tabCompleter;
    private final TuxCommand commandObject;

    protected InternalBaseCommand(BaseCommand annotation, Method methodToInvoke, Method tabCompleter, TuxCommand commandObject) {
        this.annotation = annotation;
        this.methodToInvoke = methodToInvoke;
        this.tabCompleter = tabCompleter;
        this.commandObject = commandObject;
    }
    public void invoke(String[] args, CommandSender sender, String commandUsed) {
        try {
            Object[] stuff = getParameters(args, methodToInvoke, commandObject,sender,commandUsed);
            if(stuff ==null){
                return;
            }
            methodToInvoke.invoke(commandObject,stuff);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("Failed to pass command to "+ methodToInvoke.getName());
            e.printStackTrace();
        }
    }

    public List<String> invokeTab(String[] args, CommandSender sender, String commandUsed) {
        try {
            Object[] stuff = getParameters(args, methodToInvoke, commandObject,sender,commandUsed);
            if(stuff ==null){
                return Collections.emptyList();
            }
            return (List<String>) tabCompleter.invoke(commandObject, stuff);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public BaseCommand getAnnotation() {
        return annotation;
    }

    public Method getMethodToInvoke() {
        return methodToInvoke;
    }

    public Method getTabCompleter() {
        return tabCompleter;
    }

    public TuxCommand getCommandObject() {
        return commandObject;
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
