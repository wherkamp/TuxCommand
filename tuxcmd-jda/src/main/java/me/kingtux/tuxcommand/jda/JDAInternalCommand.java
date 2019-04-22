package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.TCUtils;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.internals.BaseCommandObject;
import me.kingtux.tuxcommand.common.internals.HelpCommandObject;
import me.kingtux.tuxcommand.common.internals.InternalCommand;
import me.kingtux.tuxcommand.common.internals.SubCommandObject;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
        internalBaseCommand = TCUtils.createBaseCommandObject(tuxCommand);
        subCommands = TCUtils.createSubCommandObjects(tuxCommand);
        helpCommand = TCUtils.createHelpCommandObject(tuxCommand, BasicHelpCommand::new);
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
                if (subCommand.getCommandRules().subCommand().equalsIgnoreCase(strings[0])||TuxUtils.containsIgnoreCase(subCommand.getCommandRules().alias(), strings[0])) {
                    subCommand.execute(jdaCommandManager, new JDAArgumentSet(e.getMember(), e, TuxUtils.noNulls(TuxUtils.removeFirst(strings)), message));
                    return;
                }
            }
        }
        //If all else fails send to the base command with what we got
        internalBaseCommand.execute(jdaCommandManager, new JDAArgumentSet(e.getMember(), e, strings, message));
    }


}
