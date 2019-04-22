package me.kingtux.tuxcommand.tabcompleter.objects;

import me.kingtux.tuxcommand.common.CommandException;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;
import me.kingtux.tuxcommand.tabcompleter.annotations.BaseTabCompleter;
import me.kingtux.tuxcommand.tabcompleter.TabManager;
import me.kingtux.tuxcommand.tabcompleter.TabObject;

import java.lang.reflect.Method;

public class BaseTabCompleterObject {
    private TuxCommand tuxCommand;
    private BaseTabCompleter btc;
    private Method methodToInvoke;

    public BaseTabCompleterObject(TuxCommand tuxCommand, BaseTabCompleter btc, Method methodToInvoke) {
        this.tuxCommand = tuxCommand;
        this.btc = btc;
        this.methodToInvoke = methodToInvoke;
    }

    public void execute(TabManager commandManager, ArgumentSet as) {
        TabObject to = commandManager.getTabMaker().buildTab(methodToInvoke, tuxCommand, commandManager, as);
        if (to == null) return;
        Object[] o = commandManager.getTabMaker().createArguments(methodToInvoke, as.toArray());
        try {
            to.getExecutor().execute(tuxCommand,o);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
