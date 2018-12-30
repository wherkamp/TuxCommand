import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.Command;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@Command(aliases = {"hey"}, format = "hey test", description = "Just a test")
public class TestCommand implements TuxCommand {
    @BaseCommand
    public void baseCommand(String s, String[] args, Message message, MessageReceivedEvent e) {
        System.out.println(s + " Was just ran by" + message.getAuthor().getName());
    }

    @SubCommand(alias = "test")
    public void testSub(String s, String[] args, Message message) {
        System.out.println(s +" "+ message.getContentStripped());
    }
}
