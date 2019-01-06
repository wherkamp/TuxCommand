package me.kingtux.tuxcommand.common.internals;

import me.kingtux.tuxcommand.common.*;

import java.lang.reflect.Method;

public class HelpCommandObject {
    private TuxCommand tuxCommand;
    private Method methodToInvoke;
    private HelpCommand rules;

    /**
     * Instantiates a new Internal base command.
     *
     * @param tuxCommand     the tux command
     * @param methodToInvoke the method to invoke
     * @param rules          the rules
     */
    public HelpCommandObject(TuxCommand tuxCommand, Method methodToInvoke, HelpCommand rules) {
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
            jc.getExecutor().execute(tuxCommand, commandManager.getCommandMaker().createArguments(methodToInvoke, jdaArgumentSet.toArray()));
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}