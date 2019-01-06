package me.kingtux.tuxcommand.common;

public interface CommandObject {

    CommandExecutor getExecutor();

    TuxCommand getTuxCommand();

    Object[] getArgs();
}
