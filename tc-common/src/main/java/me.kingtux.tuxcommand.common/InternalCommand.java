package me.kingtux.tuxcommand.common;

/**
 * The Internal Command
 *
 * The Internal Command is is ran. It takes a TuxCommand and figures and runs the methods needed
 * @author KingTux
 */
public interface InternalCommand {
    /**
     * The Tux Command
     * @return tuxCommand
     */
    TuxCommand getTuxCommand();
}
