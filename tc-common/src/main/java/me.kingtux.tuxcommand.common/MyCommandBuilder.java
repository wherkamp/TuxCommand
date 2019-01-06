package me.kingtux.tuxcommand.common;

public class MyCommandBuilder {
    private MyCommand myCommand;
    private TuxCommand tuxCommand;

    private MyCommandBuilder(TuxCommand tuxCommand) {
        myCommand = new MyCommand(tuxCommand.getCommand());
        this.tuxCommand = tuxCommand;
    }

    public MyCommand getMyCommand() {
        return myCommand;
    }

    public TuxCommand getTuxCommand() {
        return tuxCommand;
    }

    public static MyCommandBuilder create(TuxCommand tc) {
        return new MyCommandBuilder(tc);
    }

    public MyCommandBuilder setAliases(String[] aliases) {
        myCommand.setAliases(aliases);
        return this;
    }

    public MyCommandBuilder setDescription(String description) {
        myCommand.setDescription(description);
        return this;
    }

    public MyCommandBuilder setFormat(String format) {
        myCommand.setFormat(format);
        return this;
    }

    public MyCommand build() {
        return myCommand;
    }
}
