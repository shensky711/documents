package cn.ut.library.logcat.bean;

import cn.ut.library.bean.LogInfo;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.bean.MemoryState;

/**
 * Created by chenhang on 2016/6/1.
 */
public class AdvancedLogInfo extends LogInfo {

    private String mTag;
    private String mThreadName;
    private String mTitle;
    private Throwable mThrowable;
    private StackTraceElement[] mStackTrace;
    private MemoryState mMemoryState;

    public AdvancedLogInfo(LogLevel level, String tag, String message) {
        super(level, message);
        this.mTag = tag;
    }

    public void apply(Builder builder) {
        this.mThreadName = builder.mThreadName;
        this.mTitle = builder.mTitle;
        this.mThrowable = builder.mThrowable;
        this.mStackTrace = builder.mStackTrace;
        this.mMemoryState = builder.mMemoryState;
    }

    public String getTag() {
        return mTag;
    }

    public String getThreadName() {
        return mThreadName;
    }

    public String getTitle() {
        return mTitle;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public StackTraceElement[] getStackTrace() {
        return mStackTrace;
    }

    public MemoryState getMemoryInfo() {
        return mMemoryState;
    }

    public static class Builder {

        private LogLevel mLogLevel;
        private String mTag;
        private String mMessage;
        private String mThreadName;
        private String mTitle;
        private Throwable mThrowable;
        private StackTraceElement[] mStackTrace;
        private MemoryState mMemoryState;

        public Builder(LogLevel logLevel, String tag, String message) {
            this.mLogLevel = logLevel;
            this.mTag = tag;
            this.mMessage = message;
        }

        public Builder threadName(String threadName) {
            mThreadName = threadName;
            return this;
        }

        public Builder title(String title) {
            mTitle = title;
            return this;
        }

        public Builder throwable(Throwable throwable) {
            mThrowable = throwable;
            return this;
        }

        public Builder stackTrace(StackTraceElement[] traceElements) {
            mStackTrace = traceElements;
            return this;
        }

        public Builder memory(MemoryState memoryState) {
            mMemoryState = memoryState;
            return this;
        }

        public AdvancedLogInfo build() {
            AdvancedLogInfo info = new AdvancedLogInfo(mLogLevel, mTag, mMessage);
            info.apply(this);
            return info;
        }
    }
}
