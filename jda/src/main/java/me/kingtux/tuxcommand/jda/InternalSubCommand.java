package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The type Internal sub command.
 */
public class InternalSubCommand {
    private TuxCommand tuxCommand;
    private Method methodToInvoke;
    private SubCommand rules;

    /**
     * Instantiates a new Internal sub command.
     *
     * @param tuxCommand     the tux command
     * @param methodToInvoke the method to invoke
     * @param rules          the rules
     */
    public InternalSubCommand(TuxCommand tuxCommand, Method methodToInvoke, SubCommand rules) {
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

    /**
     * Gets command rules.
     *
     * @return the command rules
     */
    public SubCommand getCommandRules() {
        return rules;
    }
}