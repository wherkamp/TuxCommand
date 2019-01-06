package me.kingtux.tuxcommand.jda;

import me.kingtux.simpleannotation.AnnotationWriter;
import me.kingtux.tuxcommand.common.*;
import me.kingtux.tuxcommand.common.internals.InternalCommand;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.*;

/**
 * The type Jda command manager.
 */
public class JDACommandManager
        implements CommandManager<JDAFailureHandler, JDACommandMaker>, EventListener {
    private JDA jda;
    private boolean botsRun = false;
    private List<InternalCommand> registeredCommands;
    private String prefix;
    private Map<Guild, String> guildSpecificPrefixes = new HashMap<>();
    private JDAMissingPermission permission;
    private JDAFailureHandler handler;
    private JDACommandMaker maker;

    /**
     * Instantiates a new Jda command manager.
     *
     * @param jda    the jda
     * @param prefix the prefix
     */
    public JDACommandManager(JDA jda, String prefix) {
        this.jda = jda;
        jda.addEventListener(this);
        registeredCommands = new ArrayList<>();
        this.prefix = prefix;
        permission = null;
        maker = new JDACommandMaker();
        //Use the default form!
        handler = (executor, jdaCommand) -> executor.printStackTrace();
    }

    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.getMessage().getEmbeds().isEmpty()) {
            //Throw out messages with embeds
            return;
        }
        if (!e.getMessage().getContentRaw().startsWith(prefix) ||
                !e.getMessage().getContentRaw().startsWith(guildSpecificPrefixes.getOrDefault(e.getGuild(), prefix)))
            return;
        executeCommand(e);
    }

    /**
     * Register prefix.
     *
     * @param guild  the guild
     * @param prefix the prefix
     */
    public void registerPrefix(Guild guild, String prefix) {
        guildSpecificPrefixes.put(guild, prefix);
    }

    public void onGuildLeave(GuildMemberLeaveEvent event) {
        guildSpecificPrefixes.remove(event.getGuild());
    }


    private void executeCommand(MessageReceivedEvent e) {
        String message = e.getMessage().getContentRaw();
        if (message.startsWith(prefix)) {
            message = message.replaceFirst(prefix, "");
        } else {
            String gp = guildSpecificPrefixes.get(e.getGuild());
            if (gp != null) {
                message = message.replaceFirst(gp, "");
            }
        }
        String[] messageParsed = message.split(" ");
        InternalCommand internalCommand = getCommand(messageParsed[0]);
        if (internalCommand == null) return;
        JDAInternalCommand jdaInternalCommand = (JDAInternalCommand) internalCommand;
        jdaInternalCommand.execute(messageParsed[0], TuxUtils.noNulls(removeFirst(messageParsed)), e);
    }


    private String[] removeFirst(String[] messageParsed) {
        List<String> s = new ArrayList<>(Arrays.asList(messageParsed));
        s.remove(0);
        return s.toArray(messageParsed);
    }

    @Override
    public void register(TuxCommand tuxCommand, MyCommand rules) {
        if (getInternalCommand(tuxCommand) != null) {
            try {
                throw new IllegalAccessException("Hey, You have already registered that command!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        if (rules != null) AnnotationWriter.writeToAnnotation(tuxCommand.getClass(), Command.class, rules);
        registeredCommands.add(new JDAInternalCommand(tuxCommand, this));
    }

    /**
     * Gets command.
     *
     * @param command the command
     * @return the command
     */
    public InternalCommand getCommand(String command) {
        for (InternalCommand internalCommand : registeredCommands) {
            Command rules = internalCommand.getTuxCommand().getCommand();
            if (new ArrayList<>(Arrays.asList(rules.aliases())).contains(command.toLowerCase())) {
                return internalCommand;
            }
        }
        return null;
    }

    @Override
    public InternalCommand getInternalCommand(TuxCommand t) {
        for (InternalCommand internalCommand : registeredCommands) {
            if (internalCommand.getTuxCommand() == t) {
                return internalCommand;
            }
        }
        return null;
    }

    /**
     * Can bots run boolean.
     *
     * @return the boolean
     */
    public boolean canBotsRun() {
        return botsRun;
    }

    /**
     * Can bots run.
     *
     * @param botsRun the bots run
     */
    public void canBotsRun(boolean botsRun) {
        this.botsRun = botsRun;
    }

    public JDAMissingPermission getPermission() {
        return permission;
    }

    public void setPermission(JDAMissingPermission permission) {
        this.permission = permission;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MessageReceivedEvent) {
            onMessageReceived((MessageReceivedEvent) event);
        } else if (event instanceof GuildMemberLeaveEvent) {
            onGuildLeave((GuildMemberLeaveEvent) event);
        }
    }

    public JDAFailureHandler getFailureHandler() {
        return handler;
    }

    public void setFailureHandler(JDAFailureHandler handler) {
        if (handler == null) return;
        this.handler = handler;
    }

    @Override
    public JDACommandMaker getCommandMaker() {
        return maker;
    }

    public List<TuxCommand> getRegisteredCommands() {
        List<TuxCommand> tuxCommands = new ArrayList<>();
        registeredCommands.forEach(internalCommand -> tuxCommands.add(internalCommand.getTuxCommand()));
        return tuxCommands;
    }
}
