package cn.ut.library.api;

import cn.ut.library.crash.bean.CrashInfo;

/**
 * Created by chenhang on 2016/6/3.
 */
public interface ReportApi {

    /**
     * 上报已捕获的异常
     *
     * @param throwable 捕获的异常
     */
    void reportThrowable(Throwable throwable);

    /**
     * 上报崩溃信息
     *
     * @param info 崩溃信息
     */
    void reportCrash(CrashInfo info);
}
