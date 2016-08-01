package cn.ut.library.asyncwriter.filegetter;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chenhang on 2016/6/12.
 */
public class CrashLogFileGetter implements FileGetter {

    private static final String LOG_DIR = "log";

    @Override
    public String getFileDirectory(Context context) {
        if (context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir().getAbsolutePath() + File.separator + LOG_DIR;
        }
        return null;
    }

    @Override
    public String getFilename(Context context) {
        String date = (new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)).format(new Date());
        return String.format(Locale.CHINA, "crash_%s.txt", date);
    }
}
