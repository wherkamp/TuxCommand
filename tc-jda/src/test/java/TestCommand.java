import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.Command;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.jda.RequiredPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

@Command(aliases = {"hey"}, format = "hey test", description = "Just a test")
public class TestCommand implements TuxCommand {
    @BaseCommand
    public void baseCommand(String s, TextChannel tc) {
        tc.sendMessage(s + " was sent!").queue();
    }

    @SubCommand(alias = "test")
    public MessageAction testSub(String s, String[] args, Message message) {
        return message.getChannel().sendMessage(s + " " + message.getContentStripped());
    }

    @SubCommand(alias = "null")
    public void nullCheck(TextChannel textChannel) {
        textChannel.sendMessage("Null Checking").complete();
        Object o = null;
        System.out.println(o.toString());
    }

    @RequiredPermission(permission = Permission.ADMINISTRATOR)
    @SubCommand(alias = "security")
    public void security(TextChannel textChannel) {
        textChannel.sendMessage("Security Checked").queue();
    }
}
