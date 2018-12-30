package me.kingtux.tuxcommand.common;

import java.lang.annotation.Annotation;

public class MyCommandRules implements CommandRules {
    private CommandRules commandRules;
    private String description, format;
    private String[] aliases;

    public MyCommandRules(CommandRules annotation) {
        this.commandRules = annotation;
        description = commandRules.description();
        format = commandRules.format();
        aliases = commandRules.aliases();

    }

    public CommandRules getCommandRules() {
        return commandRules;
    }

    public void setCommandRules(CommandRules commandRules) {
        this.commandRules = commandRules;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return commandRules.annotationType();
    }


    @Override
    public String description() {
        return description;
    }

    @Override
    public String[] aliases() {
        return aliases;
    }

    @Override
    public String format() {
        return format;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }
}
