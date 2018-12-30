package me.kingtux.tuxcommand.common;

public interface TuxCommand {


    default CommandRules getCommandRules() {
        return getClass().getAnnotation(CommandRules.class);
    }
}
