package me.kingtux.tuxcommand.tabcompleter;

public class MissingSubCommandExeception extends RuntimeException {
    public MissingSubCommandExeception(String s) {
        super(s);
    }
}
