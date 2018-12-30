package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.HelpCommand;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The type Internal help command.
 */
public class InternalHelpCommand {
    private TuxCommand tuxCommand;
    private Method methodToInvoke;
    private HelpCommand rules;

    /**
     * Instantiates a new Internal help command.
     *
     * @param tuxCommand     the tux command
     * @param methodToInvoke the method to invoke
     * @param rules          the rules
     */
    public InternalHelpCommand(TuxCommand tuxCommand, Method methodToInvoke, HelpCommand rules) {
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
            Object object = methodToInvoke.invoke(tuxCommand, InternalUtils.buildArguments(message, strings, methodToInvoke, message1));
            if (object == null || object.getClass() == Void.TYPE) {
                return;
            }
            if (object instanceof String) {
                message1.getChannel().sendMessage(((String) object)).queue();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets command rules.
     *
     * @return the command rules
     */
    public HelpCommand getCommandRules() {
        return rules;
    }
}
