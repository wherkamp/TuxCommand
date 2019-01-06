package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.internals.CommandMaker;
import net.dv8tion.jda.core.Permission;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JDACommandMaker implements CommandMaker<JDACommandManager, JDAArgumentSet> {
    @Override
    public JDACommand buildCommand(Method methodToInvoke, TuxCommand tuxCommand, JDACommandManager commandManager, JDAArgumentSet argumentSet) {
        //Build JDACommand
        JDACommand jdaCommand = new JDACommand();
        jdaCommand.setTuxCommand(tuxCommand);
        jdaCommand.setCommandManager(commandManager);
        jdaCommand.setArgs(createArguments(methodToInvoke, TuxUtils.arrayOf(argumentSet.getEvent(), argumentSet.getArgs(), argumentSet.getMember(), argumentSet.getMessage())));
        jdaCommand.setChannelUsed(argumentSet.getEvent().getTextChannel());
        //Build Activator
        jdaCommand.setExecutor((tuxCommand1, args1) -> {
            try {
                return methodToInvoke.invoke(tuxCommand1, args1);
            } catch (InvocationTargetException e) {
                throw new CommandException(e.getCause());
            } catch (Throwable e) {
                throw new CommandException(e);
            }
        });
        //Permission Check!
        boolean hasPermission = true;
        Permission missingPermission = null;
        //Class Check
        JDARequiredPermission classPermission = tuxCommand.getClass().getAnnotation(JDARequiredPermission.class);
        if (classPermission != null) {
            if (!argumentSet.getMember().hasPermission(classPermission.permission())) {
                hasPermission = false;
                missingPermission = classPermission.permission();
            }
        }
        //Method Check
        if (hasPermission) {
            JDARequiredPermission methodPermission = methodToInvoke.getAnnotation(JDARequiredPermission.class);
            if (methodPermission != null) {
                if (!argumentSet.getMember().hasPermission(methodPermission.permission())) {
                    hasPermission = false;
                    missingPermission = methodPermission.permission();
                }
            }
        }
        if (!hasPermission) {
            if (commandManager.getPermission() != null) {
                commandManager.getPermission().handle(jdaCommand, argumentSet, missingPermission);
            }
            return null;
        }
        return jdaCommand;
    }
}
