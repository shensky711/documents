package cn.ut.library.logcat.logger;

/**
 * Created by chenhang on 2016/6/14.
 */
public interface Logger {

    void v(String tag, String msg);

    void d(String tag, String msg);

    void i(String tag, String msg);

    void w(String tag, String msg);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable t);

    void wtf(String tag, String msg);

    void json(String tag, String json);

    void xml(String tag, String xml);
}
