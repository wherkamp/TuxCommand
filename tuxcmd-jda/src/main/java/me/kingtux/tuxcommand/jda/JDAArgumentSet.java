package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.internals.ArgumentSet;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class JDAArgumentSet implements ArgumentSet {
    private Member member;
    private MessageReceivedEvent event;
    private String[] args;
    private String message;
    private TextChannel channel;
    private User user;
    private Message messageO;
    private Guild guild;

    public JDAArgumentSet(Member member, MessageReceivedEvent event, String[] args, String message) {
        this.member = member;
        this.event = event;
        this.args = args;
        this.message = message;
        channel = event.getTextChannel();
        user = member.getUser();
        guild = member.getGuild();
        messageO = event.getMessage();

    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public void setEvent(MessageReceivedEvent event) {
        this.event = event;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    public Message getMessageO() {
        return messageO;
    }

    public Guild getGuild() {
        return guild;
    }

    @Override
    public Object[] toArray() {
        return TuxUtils.arrayOf(member, channel, event, messageO, message, args, guild, user);
    }
}
