package me.kingtux.tuxcommand.common;

/**
 * implement to make a command
 */
public interface TuxCommand {

    /**
     * A helper method to get the Command Rules
     * @return the command rules
     */
    default Command getCommand() {
        return getClass().getAnnotation(Command.class);
    }
}
