package fr.tikione.steam.cleaner.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Log file handler.
 */
public class Log {

    /** Default logger. */
    protected static Logger messagesLogger;

    static {
        try {
            Properties conf = new Properties();
            conf.load(new FileReader(new File("conf" + File.separatorChar + "log4j.properties")));
            conf.setProperty("log4j.appender.messages.File", System.getenv("USERPROFILE") + "/.tikione/log/steamcleaner_messages.log");
            PropertyConfigurator.configure(conf);
            messagesLogger = Logger.getLogger("fr.tikione.steam.cleaner.log.info");
        } catch (IOException ex) {
            throw new RuntimeException("Cannot instantiate Log4j", ex);
        }
    }

    /** Suppresses default constructor, ensuring non-instantiability. */
    private Log() {
    }

    /**
     * Log a message object with the INFO Level.
     *
     * @param message the emssage to log.
     */
    public static void info(String message) {
        messagesLogger.info(message);
    }

    /**
     * Log a message object with the ERROR Level.
     *
     * @param ex the exception to log.
     */
    public static void error(Throwable ex) {
        messagesLogger.error("", ex);
    }

    /**
     * Log a message object with the ERROR Level.
     *
     * @param message the emssage to log.
     * @param ex the exception to log.
     */
    public static void error(String message, Throwable ex) {
        messagesLogger.error(message, ex);
    }
}
