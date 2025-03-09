package pos.api.teampixl.org.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public enum LogLevel { DEBUG, INFO, WARN, ERROR }
    private static LogLevel threshold = LogLevel.DEBUG;
    private static final String LOG_FILE = "application.log";
    private static FileWriter writer;
    private static final Object lock = new Object();
    private final String name;

    static {
        try {
            writer = new FileWriter(LOG_FILE, true);
        } catch (IOException e) {
        }
    }

    private Logger(String name) {
        this.name = name;
    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    public static void setThreshold(LogLevel level) {
        threshold = level;
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message, null);
    }

    public void info(String message) {
        log(LogLevel.INFO, message, null);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message, null);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message, null);
    }

    public void error(String message, Throwable t) {
        log(LogLevel.ERROR, message, t);
    }

    private void log(LogLevel level, String message, Throwable t) {
        if (level.ordinal() < threshold.ordinal()) return;
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String logMessage = timestamp + " [" + level + "] (" + name + ") - " + message;
        System.out.println(logMessage);
        synchronized (lock) {
            try {
                writer.write(logMessage + "\n");
                if (t != null) {
                    writer.write(t.toString() + "\n");
                }
                writer.flush();
            } catch (IOException e) {
            }
        }
    }

    public static void close() {
        synchronized (lock) {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
