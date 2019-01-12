import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MessageFactory {

    private Message message;

    private EmbedBuilder builder;
    private int selfDestruct;

    public MessageFactory(String description) {
        this();
        setDescription(description);
    }

    public MessageFactory() {
        this.builder = new EmbedBuilder();
        selfDestruct = 0;
    }

    public MessageFactory setTitle(String title, String url) {
        builder.setTitle(title, url);
        return this;
    }

    public MessageFactory setDescription(String description) {
        builder.setDescription(description);
        return this;
    }

    public MessageFactory setTitle(String title) {
        builder.setTitle(title);
        return this;
    }

    public MessageFactory setImage(String image) {
        builder.setImage(image);
        return this;
    }

    public MessageFactory setColor(Color color) {
        builder.setColor(color);
        return this;
    }

    public MessageFactory addField(String name, String value, boolean inLine) {
        builder.addField(name, value, inLine);
        return this;
    }

    public MessageFactory addThumbnail(String url) {
        builder.setThumbnail(url);
        return this;
    }

    public MessageFactory setAuthor(String name, String url, String iconURL) {
        builder.setAuthor(name, url, iconURL);
        return this;
    }

    public MessageFactory setTimestamp() {
        builder.setTimestamp(OffsetDateTime.now());
        return this;
    }

    public MessageFactory setAuthor(String name, String iconURL) {
        builder.setAuthor(name, iconURL, iconURL);
        return this;
    }

    public MessageFactory queue(TextChannel channel, Consumer<Message> success) {
        try {
            success.andThen(successMessage -> {
                this.message = successMessage;
                destroy();
            });
            channel.sendMessage(builder.build()).queue(success);
        } catch (PermissionException ex) {
            System.out.println("I do not have permission: " + ex.getPermission().getName() + " on server " + channel.getGuild().getName() + " in channel: " + channel.getName());
        }
        return this;
    }

    public MessageFactory queue(TextChannel channel) {
        try {
            channel.sendMessage(builder.build()).queue(successMessage -> this.message = successMessage);
            destroy();
        } catch (PermissionException ex) {
            System.out.println("I do not have permission: " + ex.getPermission().getName() + " on server " + channel.getGuild().getName() + " in channel: " + channel.getName());
        }
        return this;
    }

    public Message complete(TextChannel channel) {
        try {
            message = channel.sendMessage(builder.build()).complete();
            destroy();
            return message;
        } catch (PermissionException ex) {
            System.out.println("I do not have permission: " + ex.getPermission().getName() + " on server " + channel.getGuild().getName() + " in channel: " + channel.getName());
            return null;
        }
    }

    public void send(User member) {
        member.openPrivateChannel().complete().sendMessage(builder.build()).queue();
        destroy();
    }

    public void send(User member, Consumer<Message> success) {
        success.andThen(message -> this.message = message);
        member.openPrivateChannel().queue(s -> s.sendMessage(builder.build()).queue(success));
    }

    public MessageFactory sendUser(User member) {
        member.openPrivateChannel().complete().sendMessage(builder.build()).queue(message1 -> this.message = message1);
        destroy();
        return this;
    }

    public MessageFactory addReaction(String reaction) {
        if (message == null) {
            throw new NullPointerException("Message must not be null!");
        }
        message.addReaction(reaction).queue();
        return this;
    }

    public MessageFactory selfDestruct(int time) {
        this.selfDestruct = time;
        return this;
    }

    private void destroy() {
        if (getSelfDestruct() == 0) return;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            if (message != null) message.delete().queue();
            executor.shutdown();
        }, getSelfDestruct(), TimeUnit.SECONDS);
    }

    public MessageEmbed build() {
        return builder.build();
    }

    public MessageFactory setThumbnail(String thumbnail) {
        builder.setThumbnail(thumbnail);
        return this;
    }

    public MessageFactory footer(String s, String iconURL) {
        builder.setFooter(s, iconURL);
        return this;
    }

    public static MessageFactory create() {
        return new MessageFactory();
    }

    public static MessageFactory create(String s) {
        return new MessageFactory(s);
    }

    public static void sendPlainMessage(String s, TextChannel c) {
        if (c.canTalk()) {
            c.sendMessage(s).queue();
        } else {
            System.out.println("No permission to speak in " + c.getName() + " channel on " + c.getGuild().getName() + " guild.");
        }
    }

    public Message getMessage() {
        return message;
    }

    public EmbedBuilder getBuilder() {
        return builder;
    }

    public int getSelfDestruct() {
        return selfDestruct;
    }
}
