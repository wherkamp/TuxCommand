package me.kingtux.tuxcommand.tabcompleter;

import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;

import java.lang.reflect.Method;

public interface TabMaker<CM extends TabManager, AS extends ArgumentSet> {

    default Object[] createArguments(Method method, Object[] args) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            //If the type is missing it will be null
            parameters[i] = getByType(args, type);
        }
        return parameters;
    }

    default Object getByType(Object[] args, Class<?> type) {
        for (Object o : args) {
            if (o.getClass() == type) {
                return o;
            } else if (type.isInstance(o)) {
                return o;
            }
        }
        return null;
    }

    public TabObject buildTab(Method methodToInvoke, TuxCommand tuxCommand, CM commandManager, AS argumentSet);
}
