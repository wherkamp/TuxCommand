package me.kingtux.tuxcommand.bukkit;

public @interface TabCompleter {
    TYPE type();

    String subCommandToEffect() default "";

    public static enum TYPE {
        BASE,
        SUB_COMMAND;
    }
}
