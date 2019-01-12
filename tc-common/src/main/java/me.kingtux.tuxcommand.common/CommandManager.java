package me.kingtux.tuxcommand.common;

import me.kingtux.tuxcommand.common.annotations.Command;
import me.kingtux.tuxcommand.common.internals.CommandMaker;
import me.kingtux.tuxcommand.common.internals.InternalCommand;

import java.util.List;

/**
 * A command manager.
 *
 * @author KingTux
 */
public interface CommandManager<EH extends FailureHandler, CM extends CommandMaker> {

    default void register(MyCommandBuilder tuxCommand) {
        register(tuxCommand.getTuxCommand(), tuxCommand.getMyCommand());
    }

    default void register(TuxCommand tuxCommand) {
        register(tuxCommand, new MyCommand(tuxCommand.getClass().getAnnotation(Command.class)));
    }

    default MyCommand fixRules(final MyCommand myCommand) {
        if(myCommand.format().toLowerCase().contains("{prefix}")){
            MyCommand m = myCommand;
            m.setFormat(m.format().replace("{prefix}", getPrefix()));
            return m;
        }
        return myCommand;
    }

    void register(TuxCommand tuxCommand, MyCommand rules);

    InternalCommand getInternalCommand(TuxCommand t);

    EH getFailureHandler();

    void setFailureHandler(EH eh);

    CM getCommandMaker();

    List<TuxCommand> getRegisteredCommands();

    default String getPrefix() {
        return "/";
    }
}
