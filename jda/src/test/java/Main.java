import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.jda.JDACommandManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        JDA jda = new JDABuilder("NTI4NjAyMTM5MDczODM5MTE2.Dwkq4g.TWMsMGG7L4d7fPQgqV1MC1QdpVw").build();
        CommandManager manager = new JDACommandManager(jda, ">");
        manager.register(new TestCommand());

    }
}
