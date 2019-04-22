package me.kingtux.tuxcommand.tabcompleter;

import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxcommand.common.TCUtils;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.exceptions.ToManyBaseAnnotationsExeception;
import me.kingtux.tuxcommand.common.internals.SubCommandObject;
import me.kingtux.tuxcommand.tabcompleter.annotations.BaseTabCompleter;
import me.kingtux.tuxcommand.tabcompleter.annotations.SubTabCompleter;
import me.kingtux.tuxcommand.tabcompleter.objects.BaseTabCompleterObject;
import me.kingtux.tuxcommand.tabcompleter.objects.SubTabCompleterObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TCTUtils {
    public static BaseTabCompleterObject getBaseTab(TuxCommand tc) {

        if (MethodFinder.getNumberOfMethodsWithAnnotation(tc.getClass(), BaseTabCompleter.class) != 1)
            throw new ToManyBaseAnnotationsExeception("The number of @BaseTabCompleter !=1");
        Method method = MethodFinder.getFirstMethodWithAnnotation(tc.getClass(), BaseTabCompleter.class);
        return new BaseTabCompleterObject(tc, method.getAnnotation(BaseTabCompleter.class), method);
    }

    public static List<SubTabCompleterObject> getSubTab(TuxCommand tc) {
        List<SubTabCompleterObject> subTabs = new ArrayList<>();
        List<SubCommandObject> subs = TCUtils.createSubCommandObjects(tc);
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(tc.getClass(), SubTabCompleter.class)) {
            SubTabCompleter stc = method.getAnnotation(SubTabCompleter.class);
            boolean foundSubCommand = false;
            for (SubCommandObject sc : subs) {
                if (sc.getCommandRules().subCommand().equalsIgnoreCase(stc.sub())) {
                    subTabs.add(new SubTabCompleterObject(tc, stc, method));
                    foundSubCommand = true;
                    break;
                }
            }
            if (!foundSubCommand) {
                try {
                    throw new MissingSubCommandExeception(stc.sub() + "Does not exist inside " + tc.getClass().getSimpleName());
                } catch (MissingSubCommandExeception e) {
                    e.printStackTrace();
                }
            }
        }
        return subTabs;
    }
}
