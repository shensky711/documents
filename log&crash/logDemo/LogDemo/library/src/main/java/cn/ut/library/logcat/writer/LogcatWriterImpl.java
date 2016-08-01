package cn.ut.library.logcat.writer;

import android.content.Context;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.ut.library.asyncwriter.AsyncFileWriterImpl;
import cn.ut.library.asyncwriter.filegetter.FileGetter;
import cn.ut.library.logcat.bean.AdvancedLogInfo;

/**
 * Created by chenhang on 2016/6/14.
 */
public class LogcatWriterImpl extends AsyncFileWriterImpl<AdvancedLogInfo> {

    public LogcatWriterImpl(Context context, FileGetter fileGetter) {
        super(context, fileGetter);
    }

    private String getTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date(System.currentTimeMillis()));
    }

    @Override
    protected void doWrite(FileWriter writer, AdvancedLogInfo content) throws IOException {
        writer.append(String.format("[%s] [%s] %s", getTimestamp(), content.getTag(), content.getMessage())).append("\n");
    }
}
