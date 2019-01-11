package me.kingtux.tuxcommand;

import me.kingtux.tuxcommand.bukkit.BasicHelpCommand;
import me.kingtux.tuxcommand.common.TCUtils;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.internals.BaseCommandObject;
import me.kingtux.tuxcommand.common.internals.HelpCommandObject;
import me.kingtux.tuxcommand.common.internals.InternalCommand;
import me.kingtux.tuxcommand.common.internals.SubCommandObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class BukkitCommandExecutor extends Command implements InternalCommand {
    private TuxCommand tuxCommand;
    private BaseCommandObject internalBaseCommand;
    private List<SubCommandObject> subCommands;
    private HelpCommandObject helpCommand;
    private BukkitCommandManager bukkitCommandManager;

    protected BukkitCommandExecutor(TuxCommand tc, BukkitCommandManager bukkitCommandManager) {
        this(tc, bukkitCommandManager, tc.getCommand().aliases()[0], tc.getCommand().description(), tc.getCommand().format(), Arrays.asList(TuxUtils.noNulls(TuxUtils.removeFirst(tc.getCommand().aliases()))));
    }

    protected BukkitCommandExecutor(TuxCommand tc, BukkitCommandManager bukkitCommandManager, String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.tuxCommand = tc;
        internalBaseCommand = TCUtils.createBaseCommandObject(tuxCommand);
        subCommands = TCUtils.createSubCommandObjects(tuxCommand);
        helpCommand = TCUtils.createHelpCommandObject(tuxCommand, BasicHelpCommand::new);
        this.bukkitCommandManager = bukkitCommandManager;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0) {
            internalBaseCommand.execute(bukkitCommandManager, new BukkitArgumentSet(commandSender, strings, s));
            return true;
        }
        if ("help".equalsIgnoreCase(strings[0])) {
            if (helpCommand != null) {
                helpCommand.execute(bukkitCommandManager, new BukkitArgumentSet(commandSender, strings, s));
                return true;
            }
        }
        if (!subCommands.isEmpty()) {
            for (SubCommandObject subCommand : subCommands) {
                if (subCommand.getCommandRules().subCommand().equalsIgnoreCase(strings[0]) || TuxUtils.containsIgnoreCase(subCommand.getCommandRules().alias(), strings[0])) {
                    subCommand.execute(bukkitCommandManager, new BukkitArgumentSet(commandSender, TuxUtils.noNulls(TuxUtils.removeFirst(strings)), s));
                    return true;
                }
            }
        }
        //If all else fails send to the base command with what we got
        internalBaseCommand.execute(bukkitCommandManager, new BukkitArgumentSet(commandSender, strings, s));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }

    @Override
    public TuxCommand getTuxCommand() {
        return tuxCommand;
    }
}
