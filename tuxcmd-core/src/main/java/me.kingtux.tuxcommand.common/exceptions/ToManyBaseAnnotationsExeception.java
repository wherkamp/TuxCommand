package me.kingtux.tuxcommand.common.exceptions;

public class ToManyBaseAnnotationsExeception extends RuntimeException {

    public ToManyBaseAnnotationsExeception() {
        super("The Number of @BaseCommand !=1 ");
    }
    public ToManyBaseAnnotationsExeception(String s) {
        super(s);
    }
}
