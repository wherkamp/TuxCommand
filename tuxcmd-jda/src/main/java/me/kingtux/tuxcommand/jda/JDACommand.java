package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.CommandExecutor;
import me.kingtux.tuxcommand.common.CommandObject;
import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * This is a command Object. This is the args that are sent. The executor,
 *
 * @author KingTux
 */
public class JDACommand implements CommandObject {
    private Object[] args;
    private TuxCommand tuxCommand;
    private CommandExecutor executor;
    private JDACommandManager commandManager;
    private TextChannel channelUsed;

     public JDACommand() {

    }

    public TextChannel getChannelUsed() {
        return channelUsed;
    }

     void setChannelUsed(TextChannel channelUsed) {
        this.channelUsed = channelUsed;
    }

    public Object[] getArgs() {
        return args;
    }

    public TuxCommand getTuxCommand() {
        return tuxCommand;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    void setArgs(Object[] args) {
        this.args = args;
    }

    void setTuxCommand(TuxCommand tuxCommand) {
        this.tuxCommand = tuxCommand;
    }

    void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public JDACommandManager getCommandManager() {
        return commandManager;
    }

    void setCommandManager(JDACommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
