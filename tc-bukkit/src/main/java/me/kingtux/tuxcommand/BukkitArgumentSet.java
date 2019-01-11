package me.kingtux.tuxcommand;

import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;
import org.bukkit.command.CommandSender;

public class BukkitArgumentSet implements ArgumentSet {
    private CommandSender sender;
    private String[] args;
    private String command;

    public BukkitArgumentSet(CommandSender sender, String[] args, String command) {
        this.sender = sender;
        this.args = args;
        this.command = command;
    }

    @Override
    public Object[] toArray() {
        return TuxUtils.arrayOf(sender, args, command);
    }

    public CommandSender getCommandSender() {
        return sender;
    }


    public String[] getArgs() {
        return args;
    }

    public String getCommand() {
        return command;
    }
}
