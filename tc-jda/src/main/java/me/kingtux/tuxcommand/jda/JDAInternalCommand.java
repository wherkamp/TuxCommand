package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.InternalCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * The type Jda internal command.
 */
public class JDAInternalCommand implements InternalCommand {
    private TuxCommand tuxCommand;
    private InternalBaseCommand internalBaseCommand;
    private List<InternalSubCommand> subCommands;
    private InternalHelpCommand helpCommand;
    private JDACommandManager jdaCommandManager;

    /**
     * Instantiates a new Jda internal command.
     *
     * @param tuxCommand the tux command
     */
    public JDAInternalCommand(TuxCommand tuxCommand, JDACommandManager commandManager) {
        this.tuxCommand = tuxCommand;
        internalBaseCommand = InternalUtils.createInternalBaseCommand(tuxCommand);
        subCommands = InternalUtils.createInternalSubCommands(tuxCommand);
        helpCommand = InternalUtils.createInternalHelpCommand(tuxCommand);
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
            internalBaseCommand.execute(message, strings, e, jdaCommandManager);
            return;
        }
        if ("help".equalsIgnoreCase(strings[0])) {
            if (helpCommand != null) {
                helpCommand.execute(message, strings, e, jdaCommandManager);
                return;
            }
        }
        if (!subCommands.isEmpty()) {
            for (InternalSubCommand subCommand : subCommands) {
                if (TuxUtils.contains(subCommand.getCommandRules().alias(), strings[0])) {
                    subCommand.execute(message, TuxUtils.noNulls(TuxUtils.removeFirst(strings)), e, jdaCommandManager);
                    return;
                }
            }
        }
        //If all else fails send to the base command with what we got
        internalBaseCommand.execute(message, strings, e,jdaCommandManager);
    }
}
