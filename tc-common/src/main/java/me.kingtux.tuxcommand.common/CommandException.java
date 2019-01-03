package me.kingtux.tuxcommand.common;

public class CommandException extends Exception {
    private final Throwable cause;

    public CommandException(TuxCommand tc, Throwable throwable) {
        super("Failed to execute " + tc.getCommand().aliases()[0]);
        cause = throwable;
    }

    public CommandException(Throwable cause) {
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }
}
