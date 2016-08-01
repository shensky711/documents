package cn.ut.logdemo.application;

import android.app.Application;
import android.content.Context;

import java.io.IOException;

import cn.ut.library.asyncwriter.AsyncFileWriter;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.bean.MemoryState;
import cn.ut.library.crash.CrashHandler;
import cn.ut.library.crash.bean.CrashInfo;
import cn.ut.library.crash.callback.CrashListener;
import cn.ut.library.logcat.LogConfiguration;
import cn.ut.library.logcat.UTLog;
import cn.ut.library.logcat.bean.AdvancedLogInfo;
import cn.ut.library.logcat.getter.MemoryGetter;
import cn.ut.library.logcat.getter.StackTraceGetter;
import cn.ut.library.logcat.getter.StringFormatter;
import cn.ut.library.logcat.getter.ThreadGetter;
import cn.ut.library.logcat.logger.LoggerBuilder;
import cn.ut.library.logcat.printer.Printer;
import cn.ut.logdemo.report.ReportActivity;

/**
 * Created by chenhang on 2016/6/15.
 */
public class TestApplication extends Application {

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = TestApplication.this;

        //注册Crash监听器
        setupCrashHandler();

        //初始化Log
        UTLog.setup(getDefaultBuilder()/*getLogBuilder()*/);
    }

    private void setupCrashHandler() {

        CrashListener crashListener = new CrashListener() {
            @Override
            public boolean onCrash(Context context, CrashInfo crashInfo) {
                ReportActivity.startup(context, crashInfo.toString()); //启动新的页面，显示出错信息
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            }
        };
        CrashHandler.register(mContext, crashListener);
    }


    private LoggerBuilder getDefaultBuilder() {
        return new LoggerBuilder(mContext);
    }

    //自定义配置
    private LoggerBuilder getLogBuilder() {
        LoggerBuilder builder = new LoggerBuilder(mContext);

        //可在这设置各种输出样式，最简单如使用系统的Log.d|v|e等
        builder.setPrinter(new Printer() {
            @Override
            public void print(AdvancedLogInfo logInfo) {

            }
        });

        //实现你自己的消息记录器，如这这把Log写入到文件系统
        builder.setLogWriter(new AsyncFileWriter<AdvancedLogInfo>() {
            @Override
            public void write(AdvancedLogInfo logInfo) {

            }

            @Override
            public void close() throws IOException {

            }
        });

        //获取内存信息
        builder.setMemoryGetter(new MemoryGetter() {
            @Override
            public MemoryState getMemory() {
                return null;
            }
        });

        //返回自定义的Thread信息,此回调会在打印语句所在线程调用
        builder.setThreadGetter(new ThreadGetter() {
            @Override
            public String getThreadInfo() {
                return null;
            }
        });

        //返回当前调用栈
        builder.setStackTraceGetter(new StackTraceGetter() {
            @Override
            public StackTraceElement[] getStackTrace(Class caller) {
                return new StackTraceElement[0];
            }
        });

        //格式化json字符串，让输出更美观
        builder.setJsonFormatter(new StringFormatter() {
            @Override
            public String format(String string) throws Exception {
                return "return your formatted json here";
            }
        });

        //格式化xml字符串，让输出更美观
        builder.setXmlFormatter(new StringFormatter() {
            @Override
            public String format(String string) throws Exception {
                return "return your formatted xml here";
            }
        });

        //配置Log
        builder.setConfiguration(new LogConfiguration(true, LogLevel.DEBUG, "")); // 设置是否输出、打印级别、全局的tag前缀

        return builder;
    }
}
