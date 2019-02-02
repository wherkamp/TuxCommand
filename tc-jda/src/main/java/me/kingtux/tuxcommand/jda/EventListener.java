package me.kingtux.tuxcommand.jda;

import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
    private JDACommandManager jcm;

    EventListener(JDACommandManager jcm) {
        this.jcm = jcm;
    }

    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.getMessage().getEmbeds().isEmpty()) {
            //Throw out messages with embeds
            return;
        }
        if (!e.getMessage().getContentRaw().startsWith(jcm.prefix) ||
                !e.getMessage().getContentRaw().startsWith(jcm.guildSpecificPrefixes.getOrDefault(e.getGuild(), jcm.prefix)))
            return;
        jcm.executeCommand(e);
    }

    public void onGuildLeave(GuildMemberLeaveEvent event) {
        jcm.guildSpecificPrefixes.remove(event.getGuild());
    }

}
