package cn.ut.library.crash;

import android.content.Context;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;

import cn.ut.library.bean.AppInfo;
import cn.ut.library.bean.CpuInfo;
import cn.ut.library.bean.DeviceInfo;
import cn.ut.library.bean.MemoryState;
import cn.ut.library.bean.SettingState;
import cn.ut.library.bean.StorageState;
import cn.ut.library.crash.bean.CrashInfo;
import cn.ut.library.crash.callback.CrashListener;
import cn.ut.library.crash.writer.CrashWriterImpl;
import cn.ut.library.asyncwriter.filegetter.CrashLogFileGetter;
import cn.ut.library.asyncwriter.filegetter.FileGetter;


public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private CrashListener mListener;
    private FileGetter mFileGetter;
    private Context mContext;

    public static void register(Context context, CrashListener listener) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context, listener, null));
    }

    public static void register(Context context, CrashListener listener, FileGetter fileGetter) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context, listener, fileGetter));
    }

    private CrashHandler(Context context, CrashListener listener, FileGetter fileGetter) {
        mContext = context.getApplicationContext();
        mListener = listener;
        mFileGetter = fileGetter == null ? new CrashLogFileGetter() : fileGetter;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        CrashInfo crashInfo = buildCrashInfo(thread, ex);
        writeLog(crashInfo);
        Log.e(TAG, crashInfo.toString());
        if (mListener != null) {
            if (mListener.onCrash(mContext, crashInfo)) {
                return;
            }
        }
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }

        ex.printStackTrace();
    }

    private CrashInfo buildCrashInfo(Thread thread, Throwable ex) {
        CrashInfo crashInfo = null;
        try {
            //遍历查找真正的错误原因
            Throwable causeBy = ex;
            while (causeBy.getCause() != null) {
                causeBy = causeBy.getCause();
            }

            crashInfo = new CrashInfo(causeBy, thread, MemoryState.capture(), StorageState.capture(), new AppInfo(mContext), SettingState.capture(mContext), new DeviceInfo(), CpuInfo.getCpuInfo());
        } catch (Throwable t) {
            Log.e(TAG, "oops, build crashInfo failure");
            t.printStackTrace();
        }

        return crashInfo;
    }

    private void writeLog(CrashInfo crashInfo) {
        CrashWriterImpl writer = new CrashWriterImpl(mContext, mFileGetter);
        writer.write(crashInfo);
    }
}
