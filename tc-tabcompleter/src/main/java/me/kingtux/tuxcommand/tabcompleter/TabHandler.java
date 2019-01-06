package me.kingtux.tuxcommand.tabcompleter;

/**
 * Implement this into the CommandManager to suppor tab
 */
public interface TabHandler {
    default boolean supportTabs() {
        return true;
    }
}
