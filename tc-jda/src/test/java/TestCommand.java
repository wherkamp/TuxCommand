import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.Command;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.jda.RequiredPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@Command(aliases = {"hey"}, format = "hey test", description = "Just a test")
public class TestCommand implements TuxCommand {
    @BaseCommand
    public void baseCommand(String s, String[] args, Message message, MessageReceivedEvent e) {
        Object o = null;
        System.out.println(o.toString());
    }

    @SubCommand(alias = "test")
    public void testSub(String s, String[] args, Message message) {
        System.out.println(s + " " + message.getContentStripped());
    }

    @RequiredPermission(permission = Permission.ADMINISTRATOR)
    @SubCommand(alias = "security")
    public void security(TextChannel textChannel) {
        textChannel.sendMessage("Security Checked").queue();
    }
}
