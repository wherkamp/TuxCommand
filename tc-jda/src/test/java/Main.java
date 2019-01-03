import me.kingtux.tuxcommand.jda.JDACommandManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws LoginException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JDA jda = new JDABuilder("NTI4NjAyMTM5MDczODM5MTE2.Dwkq4g.TWMsMGG7L4d7fPQgqV1MC1QdpVw").build();
        JDACommandManager manager = new JDACommandManager(jda, ">");
        manager.setPermission((permission, member, textChannel, tuxCommand) -> textChannel.sendMessage("Bad Person").queue());
        manager.register(new TestCommand());
    }


}
