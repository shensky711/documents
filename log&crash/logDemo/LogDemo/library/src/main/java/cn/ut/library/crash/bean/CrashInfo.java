package cn.ut.library.crash.bean;

import java.io.Serializable;

import cn.ut.library.bean.AppInfo;
import cn.ut.library.bean.CpuInfo;
import cn.ut.library.bean.DeviceInfo;
import cn.ut.library.bean.MemoryState;
import cn.ut.library.bean.SettingState;
import cn.ut.library.bean.StorageState;

/**
 * Created by chenhang on 2016/6/1.
 */
public class CrashInfo implements Serializable {

    private static final long serialVersionUID = 2024614859210206740L;

    private Throwable mCauseBy;
    private Thread mThread;
    private MemoryState mMemoryState;
    private StorageState mStorageState;
    private AppInfo mAppInfo;
    private SettingState mSettingState;
    private DeviceInfo mDeviceInfo;
    private CpuInfo mCpuInfo;

    public CrashInfo(Throwable causeBy, Thread thread, MemoryState mMemoryState, StorageState mStorageState, AppInfo mAppInfo, SettingState mSettingState, DeviceInfo deviceInfo, CpuInfo cpuInfo) {
        this.mCauseBy = causeBy;
        this.mThread = thread;
        this.mMemoryState = mMemoryState;
        this.mStorageState = mStorageState;
        this.mAppInfo = mAppInfo;
        this.mSettingState = mSettingState;
        this.mDeviceInfo = deviceInfo;
        this.mCpuInfo = cpuInfo;
    }

    public Throwable getCauseBy() {
        return mCauseBy;
    }

    public StackTraceElement[] getStackTrace() {
        return mCauseBy.getStackTrace();
    }

    public Thread getThread() {
        return mThread;
    }

    public MemoryState getMemoryState() {
        return mMemoryState;
    }

    public StorageState getStorageState() {
        return mStorageState;
    }

    public AppInfo getAppInfo() {
        return mAppInfo;
    }

    public SettingState getSettingState() {
        return mSettingState;
    }

    public DeviceInfo getDeviceInfo() {
        return mDeviceInfo;
    }

    public CpuInfo getCpuInfo() {
        return mCpuInfo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("===================================  Crash Info  ===================================\n");
        builder.append(getMemoryState().toString()).append("\n");
        builder.append(getStorageState().toString()).append("\n");
        builder.append(getAppInfo().toString()).append("\n");
        builder.append(getSettingState().toString()).append("\n");
        builder.append(getDeviceInfo().toString()).append("\n");
        builder.append(getCpuInfo().toString()).append("\n");
        builder.append("--------------------------------------------------------------\n");
        builder.append("Thread: ").append(getThread().getName()).append("\n");
        builder.append("--------------------------------------------------------------\n");
        builder.append("Exception: ").append(getCauseBy().toString()).append("\n");
        StackTraceElement[] elements = getStackTrace();
        if (elements != null) {
            builder.append("StackTrace:\n");
            for (StackTraceElement element : elements) {
                builder.append(element.toString()).append("\n");
            }
        }
        builder.append("====================================================================================\n");
        return builder.toString();
    }
}
