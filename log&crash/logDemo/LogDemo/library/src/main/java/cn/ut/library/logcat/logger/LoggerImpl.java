package cn.ut.library.logcat.logger;

import cn.ut.library.asyncwriter.AsyncFileWriter;
import cn.ut.library.logcat.LogConfiguration;
import cn.ut.library.logcat.bean.AdvancedLogInfo;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.getter.MemoryGetter;
import cn.ut.library.logcat.getter.StackTraceGetter;
import cn.ut.library.logcat.getter.StringFormatter;
import cn.ut.library.logcat.getter.ThreadGetter;
import cn.ut.library.logcat.printer.Printer;
import cn.ut.library.utils.StringUtils;

/**
 * Created by chenhang on 2016/6/14.
 */
public class LoggerImpl implements Logger {

    private Printer mPrinter;
    private AsyncFileWriter<AdvancedLogInfo> mLogWriter;
    private MemoryGetter mMemoryGetter;
    private ThreadGetter mThreadGetter;
    private StackTraceGetter mStackTraceGetter;
    private StringFormatter mXmlFormatter;
    private StringFormatter mJsonFormatter;

    private LogConfiguration mConfiguration;

    LoggerImpl(LoggerBuilder builder) {
        mPrinter = builder.getPrinter();
        mLogWriter = builder.getLogWriter();
        mMemoryGetter = builder.getMemoryGetter();
        mThreadGetter = builder.getThreadGetter();
        mStackTraceGetter = builder.getStackTraceGetter();
        mXmlFormatter = builder.getXmlFormatter();
        mJsonFormatter = builder.getJsonFormatter();
        mConfiguration = builder.getConfiguration();
    }

    private final ThreadLocal<Boolean> LOCAL_WATCH_STACK = new ThreadLocal<>();
    private final ThreadLocal<String> LOCAL_TITLE = new ThreadLocal<>();
    private final ThreadLocal<Boolean> LOCAL_WATCH_THREAD = new ThreadLocal<>();
    private final ThreadLocal<Boolean> LOCAL_WATCH_MEMORY = new ThreadLocal<>();

    public LoggerImpl watchStack() {
        LOCAL_WATCH_STACK.set(Boolean.TRUE);
        return this;
    }

    public LoggerImpl title(String title) {
        LOCAL_TITLE.set(title);
        return this;
    }

    public LoggerImpl watchThread() {
        LOCAL_WATCH_THREAD.set(Boolean.TRUE);
        return this;
    }

    public LoggerImpl watchMemory() {
        LOCAL_WATCH_MEMORY.set(Boolean.TRUE);
        return this;
    }

    public LogConfiguration getConfiguration() {
        return mConfiguration;
    }

    public void v(String tag, String msg) {
        log(LogLevel.VERBOSE, tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        log(LogLevel.DEBUG, tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        log(LogLevel.INFO, tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        log(LogLevel.WARN, tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        log(LogLevel.ERROR, tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable t) {
        log(LogLevel.ERROR, tag, msg, t);
    }

    @Override
    public void wtf(String tag, String msg) {
        log(LogLevel.ASSERT, tag, msg);
    }

    @Override
    public void json(String tag, String json) {
        if (mJsonFormatter == null) {
            log(LogLevel.DEBUG, tag, json);
        } else {
            try {
                log(LogLevel.DEBUG, tag, mJsonFormatter.format(json));
            } catch (Exception e) {
                log(LogLevel.ERROR, tag, e.getMessage() + "\n" + json);
            }
        }
    }

    @Override
    public void xml(String tag, String xml) {
        if (mXmlFormatter == null) {
            log(LogLevel.DEBUG, tag, xml);
        } else {
            try {
                log(LogLevel.DEBUG, tag, mXmlFormatter.format(xml));
            } catch (Exception e) {
                log(LogLevel.ERROR, tag, e.getMessage() + "\n" + xml);
            }
        }
    }

    private void log(LogLevel logLevel, String tag, String message) {
        log(logLevel, tag, message, null);
    }

    private void log(LogLevel logLevel, String tag, String message, Throwable throwable) {
        if (mConfiguration.isDebugEnabled() && logLevel.ordinal() >= mConfiguration.getLogLevel().ordinal()) {
            String formatTag = formatTag(tag);
            AdvancedLogInfo.Builder builder = new AdvancedLogInfo.Builder(logLevel, formatTag, message);

            Boolean watchThread = LOCAL_WATCH_THREAD.get();
            if (watchThread != null && watchThread && mThreadGetter != null) {
                builder.threadName(mThreadGetter.getThreadInfo());
            }

            String title = LOCAL_TITLE.get();
            if (!StringUtils.isEmpty(title)) {
                builder.title(title);
            }

            Boolean watchStackTrace = LOCAL_WATCH_STACK.get();
            if (watchStackTrace != null && watchStackTrace && mStackTraceGetter != null) {
                builder.stackTrace(mStackTraceGetter.getStackTrace(LoggerImpl.this.getClass()));
            }

            Boolean watchMemory = LOCAL_WATCH_MEMORY.get();
            if (watchMemory != null && watchMemory && mMemoryGetter != null) {
                builder.memory(mMemoryGetter.getMemory());
            }

            if (throwable != null) {
                builder.throwable(throwable);
            }
            AdvancedLogInfo logInfo = builder.build();

            if (mPrinter != null) {
                mPrinter.print(logInfo);
            }
            if (mLogWriter != null) {
                mLogWriter.write(logInfo);
            }
        }

        removeThreadLocal();
    }

    private void removeThreadLocal() {
        LOCAL_WATCH_THREAD.remove();
        LOCAL_TITLE.remove();
        LOCAL_WATCH_STACK.remove();
        LOCAL_WATCH_MEMORY.remove();
    }

    private String formatTag(String tag) {
        if (!StringUtils.isEmpty(mConfiguration.getTagPrefix()) && !StringUtils.isEmpty(tag)) {
            return mConfiguration.getTagPrefix() + "-" + tag;
        }
        return tag;
    }
}
