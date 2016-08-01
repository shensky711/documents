package cn.ut.library.bean;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by chenhang on 2016/6/1.
 */
public class StorageState {

    private final long mBlockSize;
    private final long mAvailableBlocks;
    private final long mTotalBlocks;

    public static StorageState capture() {
        return new StorageState();
    }

    private StorageState() {
        File sdcardDir = Environment.getExternalStorageDirectory();

        StatFs statFs = new StatFs(sdcardDir.getPath());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBlockSize = statFs.getBlockSizeLong();
            mTotalBlocks = statFs.getBlockCountLong();
            mAvailableBlocks = statFs.getAvailableBlocksLong();
        } else {
            mBlockSize = statFs.getBlockSize();
            mTotalBlocks = statFs.getBlockCount();
            mAvailableBlocks = statFs.getAvailableBlocks();
        }
    }

    public long getBlockSize() {
        return mBlockSize;
    }

    public long getTotalBlocks() {
        return mTotalBlocks;
    }

    public long getAvailableBlocks() {
        return mAvailableBlocks;
    }

    @Override
    public String toString() {
        return "StorageState{" +
                "mBlockSize=" + mBlockSize +
                ", mAvailableBlocks=" + mAvailableBlocks +
                ", mTotalBlocks=" + mTotalBlocks +
                '}';
    }
}
