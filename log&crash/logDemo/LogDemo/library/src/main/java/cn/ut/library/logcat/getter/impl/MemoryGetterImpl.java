package cn.ut.library.logcat.getter.impl;

import cn.ut.library.bean.MemoryState;
import cn.ut.library.logcat.getter.MemoryGetter;

/**
 * Created by chenhang on 2016/6/14.
 */
public class MemoryGetterImpl implements MemoryGetter {

    @Override
    public MemoryState getMemory() {
        return MemoryState.capture();
    }
}
