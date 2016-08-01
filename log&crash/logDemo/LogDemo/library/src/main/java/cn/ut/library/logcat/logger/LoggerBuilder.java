package cn.ut.library.logcat.logger;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.ut.library.asyncwriter.AsyncFileWriter;
import cn.ut.library.logcat.LogConfiguration;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.bean.AdvancedLogInfo;
import cn.ut.library.logcat.getter.MemoryGetter;
import cn.ut.library.logcat.getter.StackTraceGetter;
import cn.ut.library.logcat.getter.StringFormatter;
import cn.ut.library.logcat.getter.ThreadGetter;
import cn.ut.library.logcat.getter.impl.JsonFormatter;
import cn.ut.library.logcat.getter.impl.MemoryGetterImpl;
import cn.ut.library.logcat.getter.impl.StackTraceGetterImpl;
import cn.ut.library.logcat.getter.impl.ThreadGetterImpl;
import cn.ut.library.logcat.getter.impl.XmlFormatter;
import cn.ut.library.logcat.printer.Printer;
import cn.ut.library.logcat.printer.impl.PrettyPrinter;
import cn.ut.library.logcat.writer.LogcatWriterImpl;
import cn.ut.library.asyncwriter.filegetter.LogcatFileGetter;

/**
 * Created by chenhang on 2016/6/14.
 */
public class LoggerBuilder {

    private Context mContext;
    private Printer mPrinter;
    private AsyncFileWriter<AdvancedLogInfo> mLogWriter;
    private MemoryGetter mMemoryGetter;
    private ThreadGetter mThreadGetter;
    private StackTraceGetter mStackTraceGetter;
    private StringFormatter mXmlFormatter;
    private StringFormatter mJsonFormatter;
    private LogConfiguration mConfiguration;

    public LoggerBuilder(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public Printer getPrinter() {
        return mPrinter;
    }

    public void setPrinter(Printer printer) {
        this.mPrinter = printer;
    }

    public AsyncFileWriter<AdvancedLogInfo> getLogWriter() {
        return mLogWriter;
    }

    public void setLogWriter(AsyncFileWriter<AdvancedLogInfo> logcatWriter) {
        this.mLogWriter = logcatWriter;
    }

    public MemoryGetter getMemoryGetter() {
        return mMemoryGetter;
    }

    public void setMemoryGetter(MemoryGetter memoryGetter) {
        this.mMemoryGetter = memoryGetter;
    }

    public ThreadGetter getThreadGetter() {
        return mThreadGetter;
    }

    public void setThreadGetter(ThreadGetter threadGetter) {
        this.mThreadGetter = threadGetter;
    }

    public StackTraceGetter getStackTraceGetter() {
        return mStackTraceGetter;
    }

    public void setStackTraceGetter(StackTraceGetter stackTraceGetter) {
        this.mStackTraceGetter = stackTraceGetter;
    }

    public StringFormatter getXmlFormatter() {
        return mXmlFormatter;
    }

    public void setXmlFormatter(StringFormatter xmlFormatter) {
        this.mXmlFormatter = xmlFormatter;
    }

    public StringFormatter getJsonFormatter() {
        return mJsonFormatter;
    }

    public void setJsonFormatter(StringFormatter jsonFormatter) {
        this.mJsonFormatter = jsonFormatter;
    }

    public LogConfiguration getConfiguration() {
        return mConfiguration;
    }

    public void setConfiguration(LogConfiguration configuration) {
        this.mConfiguration = configuration;
    }

    public LoggerImpl build() {

        if (mPrinter == null) {
            mPrinter = PrettyPrinter.getInstance();
        }

        if (mLogWriter == null) {
            mLogWriter = new LogcatWriterImpl(mContext, new LogcatFileGetter());
        }

        if (mMemoryGetter == null) {
            mMemoryGetter = new MemoryGetterImpl();
        }

        if (mThreadGetter == null) {
            mThreadGetter = new ThreadGetterImpl();
        }

        if (mStackTraceGetter == null) {
            mStackTraceGetter = new StackTraceGetterImpl();
        }

        if (mXmlFormatter == null) {
            mXmlFormatter = new XmlFormatter();
        }

        if (mJsonFormatter == null) {
            mJsonFormatter = new JsonFormatter();
        }

        if (mConfiguration == null) {
            mConfiguration = new LogConfiguration(true, LogLevel.DEBUG, "");
        }

        return new LoggerImpl(LoggerBuilder.this);
    }
}
