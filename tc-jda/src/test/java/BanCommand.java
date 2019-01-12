import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.TuxUtils;
import me.kingtux.tuxcommand.common.annotations.BaseCommand;
import me.kingtux.tuxcommand.common.annotations.Command;
import me.kingtux.tuxcommand.jda.JDARequiredPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

@JDARequiredPermission(permission = Permission.BAN_MEMBERS)
@Command(aliases = "ban", description = "Its a ban command idiot", format = "{prefix}ban @User {reason}")
public class BanCommand implements TuxCommand {
    @BaseCommand
    public void banHammer(TextChannel tc, Message message, String[] args) {
        //Check
        if (args.length < 2 || message.getMentionedUsers().size() != 1) {
            tc.sendMessage("You idiot " + getCommand().format()).queue();
            return;
        }
        User userToBan = message.getMentionedUsers().get(0);
        if (userToBan.isBot()) {
            tc.sendMessage("You idiot bans are for users not bots").queue();
            return;
        }
        if (!message.getMember().canInteract(tc.getGuild().getMember(userToBan))) {
            tc.sendMessage("You may not interact with him!").queue();
            return;
        }
        //Just In Case
        String userName = userToBan.getName();
        //Build Reason
        StringBuilder sb = new StringBuilder();
        for (String s : TuxUtils.removeFirst(args)) sb = sb.append(s).append(" ");
        //Ban
        tc.getGuild().getController().ban(userToBan, 1, sb.toString()).complete();
        //Finish before send
        tc.sendMessage(userName + " has been banned!").queue();
    }

}

