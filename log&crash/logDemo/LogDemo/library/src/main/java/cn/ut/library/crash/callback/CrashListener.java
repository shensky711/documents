package cn.ut.library.crash.callback;

import android.content.Context;

import cn.ut.library.crash.bean.CrashInfo;

/**
 * Created by chenhang on 2016/6/6.
 */
public interface CrashListener {

    /**
     * @return True if defaultUncaughtHandler is not desired
     */
    boolean onCrash(Context context, CrashInfo crashInfo);
}
