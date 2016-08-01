package cn.ut.library.logcat;

import cn.ut.library.bean.LogLevel;

/**
 * Created by chenhang on 2016/6/14.
 */
public class LogConfiguration {

    private boolean mDebugEnabled = true;
    private LogLevel mLogLevel = LogLevel.DEBUG;
    private String mTagPrefix = "";

    public LogConfiguration() {
    }

    public LogConfiguration(boolean debugEnabled, LogLevel logLevel, String tagPrefix) {
        this.mDebugEnabled = debugEnabled;
        this.mLogLevel = logLevel;
        this.mTagPrefix = tagPrefix;
    }

    public boolean isDebugEnabled() {
        return mDebugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.mDebugEnabled = debugEnabled;
    }

    public LogLevel getLogLevel() {
        return mLogLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.mLogLevel = logLevel;
    }

    public String getTagPrefix() {
        return mTagPrefix;
    }

    public void setTagPrefix(String tagPrefix) {
        this.mTagPrefix = tagPrefix;
    }
}
