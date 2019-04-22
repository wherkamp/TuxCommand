package me.kingtux.tuxcommand.tabcompleter;

/**
 * Implement this into the CommandManager to suppor tab
 */
public interface TabManager<TM extends TabMaker> {
    default boolean supportTabs() {
        return true;
    }
    TM getTabMaker();
}
