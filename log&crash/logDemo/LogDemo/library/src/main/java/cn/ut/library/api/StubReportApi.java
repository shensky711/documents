package cn.ut.library.api;

import cn.ut.library.crash.bean.CrashInfo;

/**
 * stub的接口，以后需替代为服务器接口
 */
public class StubReportApi implements ReportApi {

    @Override
    public void reportThrowable(Throwable throwable) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportCrash(CrashInfo info) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
