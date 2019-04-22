package me.kingtux.tuxcommand.common;

import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxcommand.common.annotations.BaseCommand;
import me.kingtux.tuxcommand.common.annotations.HelpCommand;
import me.kingtux.tuxcommand.common.annotations.SubCommand;
import me.kingtux.tuxcommand.common.internals.BaseCommandObject;
import me.kingtux.tuxcommand.common.internals.HelpCommandObject;
import me.kingtux.tuxcommand.common.internals.SubCommandObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TCUtils {
    public static HelpCommandObject createHelpCommandObject(TuxCommand tuxCommand, HelpCommandMaker helpCommandMaker) {
        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), HelpCommand.class) > 1) {
            throw new IllegalArgumentException("The TuxCommand does not provide the correct number of BaseCommands it should be 1");
        } else if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), HelpCommand.class) == 0) {
            return helpCommandMaker.make(tuxCommand);
        }
        return new HelpCommandObject(tuxCommand, MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), HelpCommand.class), MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), HelpCommand.class).getAnnotation(HelpCommand.class));
    }

    public static List<SubCommandObject> createSubCommandObjects(TuxCommand tuxCommand) {
        List<SubCommandObject> subCommands = new ArrayList<>();
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(tuxCommand.getClass(), SubCommand.class)) {
            subCommands.add(new SubCommandObject(tuxCommand, method, method.getAnnotation(SubCommand.class)));
        }
        return subCommands;
    }

    public static BaseCommandObject createBaseCommandObject(TuxCommand tuxCommand) {
        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), BaseCommand.class) != 1) {
            throw new IllegalArgumentException("The TuxCommand does not provide the correct number of BaseCommands it should be 1");
        }
        return new BaseCommandObject(tuxCommand, MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class), MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class).getAnnotation(BaseCommand.class));
    }
}
