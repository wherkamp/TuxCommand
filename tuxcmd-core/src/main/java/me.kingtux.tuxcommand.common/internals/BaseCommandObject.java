package me.kingtux.tuxcommand.common.internals;

import me.kingtux.tuxcommand.common.*;
import me.kingtux.tuxcommand.common.annotations.BaseCommand;

import java.lang.reflect.Method;

public class BaseCommandObject {
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
    public BaseCommandObject(TuxCommand tuxCommand, Method methodToInvoke, BaseCommand rules) {
        this.tuxCommand = tuxCommand;
        this.methodToInvoke = methodToInvoke;
        this.rules = rules;
    }


    public void execute(CommandManager commandManager, ArgumentSet jdaArgumentSet) {
        @SuppressWarnings("unchecked")
        CommandObject jc = commandManager.getCommandMaker().buildCommand(methodToInvoke, tuxCommand, commandManager, jdaArgumentSet);
        if (jc == null) {
            return;
        }
        try {
            Object[] o = commandManager.getCommandMaker().createArguments(methodToInvoke, jdaArgumentSet.toArray());
            jc.getExecutor().execute(tuxCommand, o);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
