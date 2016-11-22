package cn.didadu.sample.jdk8.ch3.sec01;

import java.util.function.*;
import java.util.logging.*;

public class Logging {

    /**
     * 接受Lambda函数，延迟执行
     * @param logger
     * @param message
     */
    public static void info(Logger logger, Supplier<String> message) {
        if (logger.isLoggable(Level.INFO))
            logger.info(message.get());
    }

    public static void main(String[] args) {
        double x = 3;
        double y = 4;
        info(Logger.getGlobal(), () -> "x: " + x + ", y: " + y);
    }
}
