import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.Command;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.jda.JDARequiredPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
@JDARequiredPermission(permission = Permission.ADMINISTRATOR)
@Command(aliases = {"hey"}, format = "hey test", description = "Just a test")
public class TestCommand implements TuxCommand {
    @BaseCommand
    public void baseCommand(String s, TextChannel tc) {
        if (tc == null) {
            System.out.println("Broken System");
            return;
        }
        tc.sendMessage(s + " was sent!").queue();
    }

    @SubCommand(alias = "null")
    public void nullCheck(TextChannel textChannel) {
        textChannel.sendMessage("Null Checking").complete();
        Object o = null;
        System.out.println(o.toString());
    }

    @SubCommand(alias = "security")
    public void security(TextChannel textChannel) {
        textChannel.sendMessage("Security Checked").queue();
    }
}
