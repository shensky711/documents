package cn.ut.library.logcat.getter.impl;

import cn.ut.library.logcat.getter.ThreadGetter;

/**
 * Created by chenhang on 2016/6/14.
 */
public class ThreadGetterImpl implements ThreadGetter {

    @Override
    public String getThreadInfo() {
        return Thread.currentThread().getName();
    }
}
