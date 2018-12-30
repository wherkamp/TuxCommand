package me.kingtux.tuxcommand.bukkit;

import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxcommand.common.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitInternalCommand extends Command implements InternalCommand {
    private InternalBaseCommand internalBaseCommand;
    private List<InternalSubCommand> internalSubCommands;
    private TuxCommand tuxCommand;

    protected BukkitInternalCommand(TuxCommand tuxCommand) {
        super(tuxCommand.getCommandRules().aliases()[0], tuxCommand.getCommandRules().description(), tuxCommand.getCommandRules().format(), Arrays.asList(TuxUtils.removeFirst(tuxCommand.getCommandRules().aliases())));

        this.tuxCommand = tuxCommand;

        if (MethodFinder.getNumberOfMethodsWithAnnotation(tuxCommand.getClass(), CommandRules.class) > 1) {
            throw new IllegalArgumentException("An IkeaCommand may not have more than 1 BaseCommand");
        }
        loadInternalBaseCommand();
        loadInternalSubCommands();
    }

    private Method getBaseTab() {
        Method method = MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), TabCompleter.class);
        if (method == null) {
            return null;
        }
        if (method.getAnnotation(TabCompleter.class).type() == TabCompleter.TYPE.BASE) {
            return method;
        }
        return null;
    }

    private void loadInternalBaseCommand() {
        Method method = MethodFinder.getFirstMethodWithAnnotation(tuxCommand.getClass(), BaseCommand.class);
        Method tabMethod = getBaseTab();
        TabCompleter tabCompleter = null;
        if (tabMethod != null) {
            tabCompleter = tabMethod.getAnnotation(TabCompleter.class);
        }
        internalBaseCommand = new InternalBaseCommand(method.getAnnotation(BaseCommand.class), method, tabMethod, tuxCommand);

    }

    private void loadInternalSubCommands() {
        internalSubCommands = new ArrayList<>();
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(tuxCommand.getClass(), SubCommand.class)) {
            Method tabMethod = getSubTab(method.getAnnotation(SubCommand.class).alias()[0]);
            TabCompleter tabCompleter = null;
            if (tabMethod != null) {
                tabCompleter = tabMethod.getAnnotation(TabCompleter.class);
            }
            internalSubCommands.add(new InternalSubCommand(method.getAnnotation(SubCommand.class), tabCompleter, method, tabMethod, tuxCommand));
        }
    }

    private Method getSubTab(String sub) {
        for (Method m : MethodFinder.getAllMethodsWithAnnotation(tuxCommand.getClass(), TabCompleter.class)) {
            if (m.getAnnotation(TabCompleter.class).type() == TabCompleter.TYPE.SUB_COMMAND && m.getAnnotation(TabCompleter.class).subCommandToEffect().equalsIgnoreCase(sub)) {
                return m;
            }
        }
        return null;

    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (internalSubCommands.isEmpty()) {
            internalBaseCommand.invoke(args, sender, commandLabel);
            return true;
        }
        if (args.length == 0) {
            internalBaseCommand.invoke(args, sender, commandLabel);
        } else if (args.length >= 1) {

            for (InternalSubCommand internalSubCommand : internalSubCommands) {
                if (TuxUtils.contains(internalSubCommand.getAnnotation().alias(), args[0])) {
                    List<String> d = new LinkedList<>(Arrays.asList(args));
                    d.remove(0);
                    internalSubCommand.invoke(d.toArray(new String[0]), sender, commandLabel);
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("help")) {
                //Lets Make an our own help command :)!
                sender.sendMessage(getDefaultHelp());
            }
        }
        return true;
    }

    private String[] getDefaultHelp() {
        return new String[0];
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        if (args.length == 1 || args.length == 0) {
            if (internalBaseCommand.getTabCompleter() == null) {
                List<String> strings = new ArrayList<>();
                internalSubCommands.forEach(e -> strings.addAll(Arrays.asList(e.getAnnotation().alias())));
                return strings;
            } else {
                return internalBaseCommand.invokeTab(args, sender, alias);
            }
        } else {
            for (InternalSubCommand internalSubCommand : internalSubCommands) {
                if (internalSubCommand.getTabCompleter() != null && internalSubCommand.getTabCompleterMethod() != null) {
                    if (internalSubCommand.getTabCompleter().subCommandToEffect().equalsIgnoreCase(args[0])) {
                        return internalSubCommand.invokeTab(args, sender, alias);
                    }
                }
            }
        }
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    @Override
    public TuxCommand getTuxCommand() {
        return tuxCommand;
    }
}
