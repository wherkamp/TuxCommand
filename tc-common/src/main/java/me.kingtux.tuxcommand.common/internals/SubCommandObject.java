package me.kingtux.tuxcommand.common.internals;

import me.kingtux.tuxcommand.common.*;
import me.kingtux.tuxcommand.common.annotations.SubCommand;

import java.lang.reflect.Method;

public class SubCommandObject {
    private TuxCommand tuxCommand;
    private Method methodToInvoke;
    private SubCommand rules;

    /**
     * Instantiates a new Internal base command.
     *
     * @param tuxCommand     the tux command
     * @param methodToInvoke the method to invoke
     * @param rules          the rules
     */
    public SubCommandObject(TuxCommand tuxCommand, Method methodToInvoke, SubCommand rules) {
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

    public SubCommand getCommandRules() {
        return rules;
    }
}