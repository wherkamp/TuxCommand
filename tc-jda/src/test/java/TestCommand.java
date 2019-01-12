import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.common.annotations.BaseCommand;
import me.kingtux.tuxcommand.common.annotations.Command;
import me.kingtux.tuxcommand.common.annotations.HelpCommand;
import me.kingtux.tuxcommand.common.annotations.SubCommand;
import me.kingtux.tuxcommand.jda.JDACommandManager;
import me.kingtux.tuxcommand.jda.JDARequiredPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;

@JDARequiredPermission(permission = Permission.ADMINISTRATOR)
@Command(aliases = {"hey"}, format = "hey test", description = "Just a test")
public class TestCommand implements TuxCommand {
    private JDACommandManager jdaCommandManager;

    public TestCommand(JDACommandManager jdaCommandManager) {
        this.jdaCommandManager = jdaCommandManager;
    }

    @BaseCommand
    public void baseCommand(String s, TextChannel tc) {
        if (tc == null) {
            System.out.println("Broken System");
            return;
        }
        tc.sendMessage(s + " was sent!").queue();
    }

    @SubCommand(subCommand = "null")
    public void nullCheck(TextChannel textChannel) {
        textChannel.sendMessage("Null Checking").complete();
        Object o = null;
        System.out.println(o.toString());
    }

    @SubCommand(subCommand = "bhelp")
    public void help(TextChannel tc) {
        MessageFactory messageFactory = MessageFactory.create();
        messageFactory = messageFactory.setTitle("Help Panel");
        for (TuxCommand tuxCommand : jdaCommandManager.getRegisteredCommands()) {
            JDARequiredPermission permission = tuxCommand.getClass().getAnnotation(JDARequiredPermission.class);
            messageFactory = messageFactory.addField(tuxCommand.getCommand().format(), tuxCommand.getCommand().description(), false);
        }
        messageFactory.queue(tc);
    }

    @SubCommand(subCommand = "security")
    public void security(TextChannel textChannel) {
        textChannel.sendMessage("Security Checked").queue();
    }
}
