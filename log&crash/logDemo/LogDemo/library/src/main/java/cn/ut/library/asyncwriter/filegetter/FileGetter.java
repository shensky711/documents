package cn.ut.library.asyncwriter.filegetter;

import android.content.Context;

/**
 * Created by chenhang on 2016/6/12.
 */
public interface FileGetter {

    String getFileDirectory(Context context);

    String getFilename(Context context);
}
