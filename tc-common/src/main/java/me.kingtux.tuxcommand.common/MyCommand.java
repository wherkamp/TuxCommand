package me.kingtux.tuxcommand.common;

import java.lang.annotation.Annotation;

/**
 * A simple way of editing the rules of the Cmmand
 */
public class MyCommand implements Command {
    private Command command;
    private String description, format;
    private String[] aliases;

    public MyCommand(Command annotation) {
        this.command = annotation;
        description = command.description();
        format = command.format();
        aliases = command.aliases();

    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return command.annotationType();
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
