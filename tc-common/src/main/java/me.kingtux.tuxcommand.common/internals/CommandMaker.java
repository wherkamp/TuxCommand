package me.kingtux.tuxcommand.common.internals;

import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.TuxCommand;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public interface CommandMaker<CM extends CommandManager, AS extends ArgumentSet> {

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

    //Create The CommandObject
    CommandObject buildCommand(Method methodToInvoke, TuxCommand tuxCommand, CM commandManager, AS t);

}
