package cn.ut.library.bean;

import android.os.Debug;

/**
 * Created by chenhang on 2016/6/1.
 */
public class MemoryState {

    /**
     * 虚拟机已获取到的内存
     */
    private long mTotalMemory;

    /**
     * 已获取但尚未使用的内存
     */
    private long mFreeMemory;

    /**
     * 虚拟机从系统那能获取到的最大内存
     */
    private long mMaxAvailableMemory;

    private long mHeapAllocatedMemory;

    public static MemoryState capture() {
        return new MemoryState(Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory(), Runtime.getRuntime().maxMemory(), Debug.getNativeHeapAllocatedSize());
    }

    public MemoryState() {

    }

    public MemoryState(long mTotalMemory, long mFreeMemory, long mMaxAvailableMemory, long mHeapAllocatedMemory) {
        this.mTotalMemory = mTotalMemory;
        this.mFreeMemory = mFreeMemory;
        this.mMaxAvailableMemory = mMaxAvailableMemory;
        this.mHeapAllocatedMemory = mHeapAllocatedMemory;
    }

    public long getTotalMemory() {
        return mTotalMemory;
    }

    public long getFreeMemory() {
        return mFreeMemory;
    }

    public long getHeapAllocatedMemory() {
        return mHeapAllocatedMemory;
    }

    public long getMaxAvailableMemory() {
        return mMaxAvailableMemory;
    }

    @Override
    public String toString() {
        return "MemoryState{" +
                "mTotalMemory=" + mTotalMemory +
                ", mFreeMemory=" + mFreeMemory +
                ", mMaxAvailableMemory=" + mMaxAvailableMemory +
                ", mHeapAllocatedMemory=" + mHeapAllocatedMemory +
                '}';
    }
}
