package me.kingtux.tuxcommand.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TuxCMD {
    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        TuxCMD.logger = logger;
    }

    public static void loadLogger() {
        logger = LoggerFactory.getLogger(TuxCMD.class);
    }
}
