package me.kingtux.tuxcommand.jda;

import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.HelpCommand;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Internal utils.
 */
class InternalUtils {
    /**
     * Build arguments object [ ].
     *
     * @param message              the message
     * @param args                 the args
     * @param methodToInvoke       the method to invoke
     * @param messageReceivedEvent the message received event
     * @return the object [ ]
     */
    static Object[] buildArguments(String message, String[] args, Method methodToInvoke, MessageReceivedEvent messageReceivedEvent) {
        Class<?>[] parameterTypes = methodToInvoke.getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (type == String[].class) {
                parameters[i] = args;
            } else if (type == String.class) {
                parameters[i] = message;
            } else if (type == MessageReceivedEvent.class) {
                parameters[i] = messageReceivedEvent;
            } else if (type == Guild.class) {
                parameters[i] = messageReceivedEvent.getGuild();
            } else if (type == User.class) {
                parameters[i] = messageReceivedEvent.getAuthor();
            } else if (type == Member.class) {
                parameters[i] = messageReceivedEvent.getMember();
            } else if (type == Message.class) {
                parameters[i] = messageReceivedEvent.getMessage();
            } else if (type == TextChannel.class) {
                parameters[i] = messageReceivedEvent.getChannel();
            }
        }
        return parameters;
    }

    /**
     * Create internal base command internal base command.
     *
     * @param tuxCommand the tux command
     * @return the internal base command
     */
    static InternalBaseCommand createInternalBaseCommand(TuxCommand tuxCommand) {
        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), BaseCommand.class) != 1) {
            throw new IllegalArgumentException("The TuxCommand does not provide the correct number of BaseCommands it should be 1");
        }
        return new InternalBaseCommand(tuxCommand, MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class), MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class).getAnnotation(BaseCommand.class));
    }

    /**
     * Create internal sub commands list.
     *
     * @param tuxCommand the tux command
     * @return the list
     */
    static List<InternalSubCommand> createInternalSubCommands(TuxCommand tuxCommand) {
        List<InternalSubCommand> subCommands = new ArrayList<>();
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(tuxCommand.getClass(), SubCommand.class)) {
            subCommands.add(new InternalSubCommand(tuxCommand, method, method.getAnnotation(SubCommand.class)));
        }
        return subCommands;
    }

    /**
     * Create internal help command internal help command.
     *
     * @param tuxCommand the tux command
     * @return the internal help command
     */
    static InternalHelpCommand createInternalHelpCommand(TuxCommand tuxCommand) {
        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), HelpCommand.class) > 1) {
            throw new IllegalArgumentException("The TuxCommand does not provide the correct number of BaseCommands it should be 1");
        } else if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), HelpCommand.class) == 0) {
            return new BasicHelpCommand(tuxCommand);
        }
        return new InternalHelpCommand(tuxCommand, MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), HelpCommand.class), MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), HelpCommand.class).getAnnotation(HelpCommand.class));
    }

    static void execute(Method methodToInvoke, TuxCommand tuxCommand, JDACommandManager jdaCommandManager, String message, String[] strings, MessageReceivedEvent message1) {
        if (methodToInvoke.getAnnotation(RequiredPermission.class) != null) {
            RequiredPermission permission = methodToInvoke.getAnnotation(RequiredPermission.class);
            if (!message1.getMember().hasPermission(permission.permission())) {
                if (jdaCommandManager.getPermission() != null) {
                    jdaCommandManager.getPermission().handleLackOfPermission(permission.permission(), message1.getMember(), message1.getTextChannel(), tuxCommand);
                }
                return;
            }
        }
        try {
            if (methodToInvoke.getReturnType() == Void.TYPE) {
                methodToInvoke.invoke(tuxCommand, InternalUtils.buildArguments(message, strings, methodToInvoke, message1));
                return;
            }
            Object object = methodToInvoke.invoke(tuxCommand, InternalUtils.buildArguments(message, strings, methodToInvoke, message1));
            if (object == null || object.getClass() == Void.TYPE) {
                return;
            }
            if (object instanceof String) {
                message1.getChannel().sendMessage(((String) object)).queue();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Failed to run command " + tuxCommand.getCommand().aliases()[0] + " ");
            e.printStackTrace();
        }

    }

}