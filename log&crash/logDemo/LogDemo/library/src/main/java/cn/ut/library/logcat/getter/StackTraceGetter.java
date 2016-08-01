package cn.ut.library.logcat.getter;

/**
 * Created by chenhang on 2016/6/14.
 */
public interface StackTraceGetter {

    StackTraceElement[] getStackTrace(Class caller);

}
