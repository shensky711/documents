package cn.ut.library.logcat;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.logger.LoggerBuilder;
import cn.ut.library.logcat.logger.LoggerImpl;

/**
 * Created by chenhang on 2016/6/14.
 */
public class UTLog {

    private static LoggerImpl mLoggerImpl;

    public static void setup(@NonNull LoggerBuilder builder) {
        mLoggerImpl = builder.build();
    }

    @VisibleForTesting
    public static void tearDown() {
        mLoggerImpl = null;
    }

    /**
     * 打开/关闭log开关
     *
     * @param enable 是否打开
     */
    public static void setDebugEnabled(boolean enable) {
        mLoggerImpl.getConfiguration().setDebugEnabled(enable);
    }

    /**
     * @return log开关状态
     */
    public static boolean isDebugEnabled() {
        return mLoggerImpl.getConfiguration().isDebugEnabled();
    }

    /**
     * @return 获取当前的Log level
     */
    public static LogLevel getLogLevel() {
        return mLoggerImpl.getConfiguration().getLogLevel();
    }

    /**
     * 设置log输出级别，高于该级别的log将会输出，低于的将会忽略
     */
    public static void setLogLevel(@NonNull LogLevel mLogLevel) {
        mLoggerImpl.getConfiguration().setLogLevel(mLogLevel);
    }

    /**
     * @return 全局的tag前缀
     */
    public static String getTagPrefix() {
        return mLoggerImpl.getConfiguration().getTagPrefix();
    }

    /**
     * @param prefix 全局tag前缀
     */
    public static void setTagPrefix(@NonNull String prefix) {
        mLoggerImpl.getConfiguration().setTagPrefix(prefix);
    }

    public static LoggerImpl watchStack() {
        return mLoggerImpl.watchStack();
    }

    public static LoggerImpl title(String title) {
        return mLoggerImpl.title(title);
    }

    public static LoggerImpl watchThread() {
        return mLoggerImpl.watchThread();
    }

    public static LoggerImpl watchMemory() {
        return mLoggerImpl.watchMemory();
    }

    public static void v(String tag, String msg) {
        mLoggerImpl.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        mLoggerImpl.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        mLoggerImpl.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        mLoggerImpl.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        mLoggerImpl.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        mLoggerImpl.e(tag, msg, t);
    }

    public static void wtf(String tag, String msg) {
        mLoggerImpl.wtf(tag, msg);
    }

    public static void json(String tag, String json) {
        mLoggerImpl.json(tag, json);
    }

    public static void xml(String tag, String xml) {
        mLoggerImpl.xml(tag, xml);
    }
}
