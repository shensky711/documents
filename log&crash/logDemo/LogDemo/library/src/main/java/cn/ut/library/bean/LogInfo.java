package cn.ut.library.bean;

/**
 * Created by chenhang on 2016/6/16.
 */
public class LogInfo {

    private static final char LOG_LEVEL_SEPARATOR = '/';
    private static final int END_OF_DATE_INDEX = 18;
    private static final int START_OF_MESSAGE_INDEX = 21;
    public static final int MIN_LOG_SIZE = 21;
    public static final int LOG_LEVEL_INDEX = 19;

    private final LogLevel level;
    private final String message;

    public LogInfo(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }

    /**
     * 把从logcat -v time 指令中抓取的信息转化为LogInfo对象,如："06-16 09:03:51.080 D/tag     (18480): message"
     *
     * @param logcat 抓取的logcat信息
     * @return 转化后的LogInfo对象
     * @throws IllegalArgumentException
     */
    public static LogInfo fromString(String logcat) throws IllegalArgumentException {
        if (logcat == null
                || logcat.length() < MIN_LOG_SIZE
                || logcat.charAt(20) != LOG_LEVEL_SEPARATOR) {
            throw new IllegalArgumentException(
                    logcat);
        }
        LogLevel level = LogLevel.getLogLevel(logcat.charAt(LOG_LEVEL_INDEX));
        String date = logcat.substring(0, END_OF_DATE_INDEX);
        String message = logcat.substring(START_OF_MESSAGE_INDEX, logcat.length());
        return new LogInfo(level, logcat);
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Log {" + "level=" + level + " , message=" + message + "}";
    }
}
