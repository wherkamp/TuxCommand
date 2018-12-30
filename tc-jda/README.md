# TuxCommand JDA
This is the TuxCommand JDA system. This is partly ready

### How to use

```java
import me.kingtux.tuxcommand.common.BaseCommand;
import me.kingtux.tuxcommand.common.CommandManager;
import me.kingtux.tuxcommand.common.SubCommand;
import me.kingtux.tuxcommand.common.TuxCommand;
import me.kingtux.tuxcommand.jda.JDACommandManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;

import javax.security.auth.login.LoginException;

// Step one you will need a a JDA Object
//create the CommandManager
class Main {
    public static void main(String[] args) throws LoginException {
        //The second argument is the command prefix 
        JDA jda = new JDABuilder("NO_TOKEN").build();
        CommandManager manager = new JDACommandManager(jda, ">");
        //This acts like any old manager. See the main README.md on how to use.
        manager.register(new TestCommand());
    }
}
@Command(aliases={"hey"})
class TestCommand implements TuxCommand {
    @BaseCommand
    public void baseCommand(TextChannel textChannel) {
        textChannel.sendMessage("Hello").queue();
    }

    @SubCommand(alias = "hey")
    public void testHey(TextChannel textChannel, String[] args) {
        if (args.length == 0) {
            textChannel.sendMessage("Please Provide something").queue();
        }
        textChannel.sendMessage(args[0]).queue();
        
    }
}
```
#### Supported Method Arguments
1. String the string is the command used. 
2. String[] this is the args. In a sub command the first argument is removed
3. The Following JDA Objects: Message, MessageReceivedEvent, Member, User, TextChannel, and Guild 
#### Other Info
You may return either, void (Does nothing) or a String(Will send it)


## Maven
```xml
   <repository>
      <id>kingtux-repo</id>
      <url>http://repo.kingtux.me/repository/maven-public/</url>
    </repository>
    
    <dependency>
      <groupId>me.kingtux</groupId>
      <artifactId>tc-jda</artifactId>
      <!---Make sure you use Latest Version!-->
      <version>1.0-SNAPSHOT</version>
      <scope>compile</scope>
    </dependency>
```
## Gradle
```
repositories {
  maven { url 'http://repo.kingtux.me/repository/maven-public/' }
}

dependencies {
   compile "me.kingtux:tc-jda:1.0-SNAPSHOT"
}
```