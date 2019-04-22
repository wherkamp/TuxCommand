package me.kingtux.tuxcommand.common;

import me.kingtux.tuxcommand.common.internals.HelpCommandObject;

@FunctionalInterface
public interface HelpCommandMaker {
    HelpCommandObject make(TuxCommand tc);
}
