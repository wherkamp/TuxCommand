package me.kingtux.tuxcommand.jda;

import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxcommand.common.*;
import me.kingtux.tuxcommand.common.internals.BaseCommandObject;
import me.kingtux.tuxcommand.common.internals.HelpCommandObject;
import me.kingtux.tuxcommand.common.internals.InternalCommand;
import me.kingtux.tuxcommand.common.internals.SubCommandObject;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Jda internal command.
 */
public class JDAInternalCommand implements InternalCommand {
    private TuxCommand tuxCommand;
    private BaseCommandObject internalBaseCommand;
    private List<SubCommandObject> subCommands;
    private HelpCommandObject helpCommand;
    private JDACommandManager jdaCommandManager;

    /**
     * Instantiates a new Jda internal command.
     *
     * @param tuxCommand the tux command
     */
    public JDAInternalCommand(TuxCommand tuxCommand, JDACommandManager commandManager) {
        this.tuxCommand = tuxCommand;
        internalBaseCommand = createBaseCommandObject(tuxCommand);
        subCommands = createSubCommandObjects(tuxCommand);
        helpCommand = createHelpCommandObject(tuxCommand);
        this.jdaCommandManager = commandManager;
    }

    @Override
    public TuxCommand getTuxCommand() {
        return tuxCommand;
    }

    /**
     * Execute.
     *
     * @param message the message
     * @param strings the strings
     * @param e       the e
     */
    public void execute(String message, String[] strings, MessageReceivedEvent e) {

        if (strings.length == 0) {
            internalBaseCommand.execute(jdaCommandManager, new JDAArgumentSet(e.getMember(), e, strings, message));
            return;
        }
        if ("help".equalsIgnoreCase(strings[0])) {
            if (helpCommand != null) {
                helpCommand.execute(jdaCommandManager, new JDAArgumentSet(e.getMember(), e, strings, message));
                return;
            }
        }
        if (!subCommands.isEmpty()) {
            for (SubCommandObject subCommand : subCommands) {
                if (TuxUtils.contains(subCommand.getCommandRules().alias(), strings[0])) {
                    subCommand.execute(jdaCommandManager, new JDAArgumentSet(e.getMember(), e, strings, message));
                    return;
                }
            }
        }
        //If all else fails send to the base command with what we got
        internalBaseCommand.execute(jdaCommandManager, new JDAArgumentSet(e.getMember(), e, strings, message));
    }

    private static HelpCommandObject createHelpCommandObject(TuxCommand tuxCommand) {
        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), HelpCommand.class) > 1) {
            throw new IllegalArgumentException("The TuxCommand does not provide the correct number of BaseCommands it should be 1");
        } else if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), HelpCommand.class) == 0) {
            return new BasicHelpCommand(tuxCommand);
        }
        return new HelpCommandObject(tuxCommand, MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), HelpCommand.class), MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), HelpCommand.class).getAnnotation(HelpCommand.class));
    }

    private static List<SubCommandObject> createSubCommandObjects(TuxCommand tuxCommand) {
        List<SubCommandObject> subCommands = new ArrayList<>();
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(tuxCommand.getClass(), SubCommand.class)) {
            subCommands.add(new SubCommandObject(tuxCommand, method, method.getAnnotation(SubCommand.class)));
        }
        return subCommands;
    }

    private static BaseCommandObject createBaseCommandObject(TuxCommand tuxCommand) {
        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), BaseCommand.class) != 1) {
            throw new IllegalArgumentException("The TuxCommand does not provide the correct number of BaseCommands it should be 1");
        }
        return new BaseCommandObject(tuxCommand, MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class), MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class).getAnnotation(BaseCommand.class));
    }
}
