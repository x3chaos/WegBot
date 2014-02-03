/*
 *  Author:       Shawn Lutch
 *  Project:      WegBot
 *  Description:  
 *
 *  Class:        org.x3chaos.WegBot.logging.BotLogger
 *  Description:  
 *
 *  Date:         Feb 2, 2014
 */
package org.x3chaos.WegBot.logging;

import java.util.logging.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BotLogger {

    public static final String FORMAT = "[%s] %s\n";

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);

        Handler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {

            @Override
            public String format(LogRecord record) {
                return String.format(BotLogger.FORMAT,
                        record.getLevel().getName(), record.getMessage());
            }

        });

        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        return logger;
    }

}
