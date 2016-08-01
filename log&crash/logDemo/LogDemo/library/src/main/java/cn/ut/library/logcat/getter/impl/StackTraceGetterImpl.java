package cn.ut.library.logcat.getter.impl;

import android.support.annotation.NonNull;

import cn.ut.library.logcat.getter.StackTraceGetter;

/**
 * Created by chenhang on 2016/6/14.
 */
public class StackTraceGetterImpl implements StackTraceGetter {

    @Override
    public StackTraceElement[] getStackTrace(Class caller) {
        StackTraceElement[] allTraces = Thread.currentThread().getStackTrace();
        int unusedCount = getUnusedStackCount(allTraces, caller);
        StackTraceElement[] trace = new StackTraceElement[allTraces.length - unusedCount];
        System.arraycopy(allTraces, unusedCount, trace, 0, allTraces.length - unusedCount);
        return trace;
    }

    /**
     * Determines the starting index of the stack trace
     *
     * @param trace the stack trace
     * @return the start index
     */
    private int getUnusedStackCount(@NonNull StackTraceElement[] trace, Class caller) {
        /**
         * The minimum stack trace index, starts at this class after two native calls.
         */
        final int MIN_STACK_OFFSET = 2;
        int count = MIN_STACK_OFFSET;
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            if (StackTraceGetterImpl.class.getName().equals(trace[i].getClassName()) || caller.getName().equals(trace[i].getClassName())) {
                count++;
            } else {
                return count;
            }
        }
        return -1;
    }
}
