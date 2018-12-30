package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.CommandRules;
import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The type Internal base command.
 */
public class InternalBaseCommand {
    private TuxCommand tuxCommand;
    private Method methodToInvoke;
    private BaseCommand rules;

    /**
     * Instantiates a new Internal base command.
     *
     * @param tuxCommand     the tux command
     * @param methodToInvoke the method to invoke
     * @param rules          the rules
     */
    public InternalBaseCommand(TuxCommand tuxCommand, Method methodToInvoke, BaseCommand rules) {
        this.tuxCommand = tuxCommand;
        this.methodToInvoke = methodToInvoke;
        this.rules = rules;
    }

    /**
     * Execute.
     *
     * @param message  the message
     * @param strings  the strings
     * @param message1 the message 1
     */
    public void execute(String message, String[] strings, MessageReceivedEvent message1) {
        try {
            methodToInvoke.invoke(tuxCommand, InternalUtils.buildArguments(message, strings, methodToInvoke, message1));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}