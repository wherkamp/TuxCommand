package me.kingtux.tuxcommand.common;

/**
 *
 */
public interface CommandManager {

    default void register(TuxCommand tuxCommand){
        register(tuxCommand, new MyCommandRules(tuxCommand.getClass().getAnnotation(CommandRules.class)));
    }

    void register(TuxCommand tuxCommand, MyCommandRules rules);

    InternalCommand getInternalCommand(TuxCommand t);
}
