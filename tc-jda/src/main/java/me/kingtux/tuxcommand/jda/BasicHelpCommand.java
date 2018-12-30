package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * The type Basic help command.
 */
public class BasicHelpCommand extends InternalHelpCommand {
    /**
     * Instantiates a new Basic help command.
     *
     * @param tuxCommand the tux command
     */
    public BasicHelpCommand(TuxCommand tuxCommand) {
        super(tuxCommand, null, null);
    }

    @Override
    public void execute(String message, String[] strings, MessageReceivedEvent message1) {
        message1.getChannel().sendMessage("Something Gay ignore me at the moment").queue();
    }
}
