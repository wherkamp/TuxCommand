package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;
import me.kingtux.tuxcommand.common.internals.HelpCommandObject;

public class BasicHelpCommand extends HelpCommandObject {
    public BasicHelpCommand(TuxCommand tuxCommand) {
        super(tuxCommand, null, null);
    }

    @Override
    public void execute(CommandManager commandManager, ArgumentSet jdaArgumentSet) {
        JDAArgumentSet argumentSet = (JDAArgumentSet) jdaArgumentSet;
        argumentSet.getMember().getUser().openPrivateChannel().complete().sendMessage("Comming Soon to a bot near you!").queue();
    }
}
