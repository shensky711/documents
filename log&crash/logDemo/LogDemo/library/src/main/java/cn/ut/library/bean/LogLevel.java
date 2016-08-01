package cn.ut.library.bean;

/**
 * Created by chenhang on 2016/6/14.
 */
public enum LogLevel {

    VERBOSE("V"),
    DEBUG("D"),
    INFO("I"),
    WARN("W"),
    ERROR("E"),
    ASSERT("A");

    private final String mMark;

    LogLevel(String mark) {
        this.mMark = mark;
    }

    public String getMark() {
        return mMark;
    }

    public static LogLevel getLogLevel(char mark) {
        switch (mark) {
            case 'V':
                return LogLevel.VERBOSE;
            case 'I':
                return LogLevel.INFO;
            case 'W':
                return LogLevel.WARN;
            case 'E':
                return LogLevel.ERROR;
            case 'A':
                return LogLevel.ASSERT;
            case 'D':
            default:
                return LogLevel.DEBUG;
        }
    }
}
