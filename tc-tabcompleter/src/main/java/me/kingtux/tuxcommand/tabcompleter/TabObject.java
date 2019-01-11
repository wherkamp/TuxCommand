package me.kingtux.tuxcommand.tabcompleter;

import me.kingtux.tuxcommand.common.TuxCommand;

public interface TabObject {

    TabExecutor getExecutor();

    TuxCommand getTuxCommand();

    Object[] getArgs();
}
