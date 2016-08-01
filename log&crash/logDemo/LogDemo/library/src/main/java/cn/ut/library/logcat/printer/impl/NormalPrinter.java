package cn.ut.library.logcat.printer.impl;

import android.util.Log;

import cn.ut.library.logcat.bean.AdvancedLogInfo;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.printer.Printer;


/**
 * Created by chenhang on 2016/6/1.
 */
public class NormalPrinter implements Printer {

    private static NormalPrinter sInstance;

    private NormalPrinter() {
    }

    public static NormalPrinter getInstance() {
        if (sInstance == null) {
            synchronized (NormalPrinter.class) {
                if (sInstance == null) {
                    sInstance = new NormalPrinter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void print(AdvancedLogInfo logInfo) {
        doPrint(logInfo.getLevel(), logInfo.getTag(), logInfo.getMessage());
    }

    private void doPrint(LogLevel logLevel, String tag, String message) {
        switch (logLevel) {
            case ERROR:
                Log.e(tag, message);
                break;
            case INFO:
                Log.i(tag, message);
                break;
            case VERBOSE:
                Log.v(tag, message);
                break;
            case WARN:
                Log.w(tag, message);
                break;
            case ASSERT:
                Log.wtf(tag, message);
                break;
            case DEBUG:
                // Fall through, log debug by default
            default:
                Log.d(tag, message);
                break;
        }
    }
}
