package me.kingtux.tuxcommand.common;

/**
 * A command manager.
 *
 * @author KingTux
 */
public interface CommandManager {

    default void register(TuxCommand tuxCommand){
        register(tuxCommand, new MyCommand(tuxCommand.getClass().getAnnotation(Command.class)));
    }

    void register(TuxCommand tuxCommand, MyCommand rules);

    InternalCommand getInternalCommand(TuxCommand t);
}
