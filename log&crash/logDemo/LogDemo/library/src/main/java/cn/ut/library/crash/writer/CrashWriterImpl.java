package cn.ut.library.crash.writer;

import android.content.Context;

import java.io.FileWriter;
import java.io.IOException;

import cn.ut.library.crash.bean.CrashInfo;
import cn.ut.library.asyncwriter.AsyncFileWriterImpl;
import cn.ut.library.asyncwriter.filegetter.FileGetter;

/**
 * Created by chenhang on 2016/6/14.
 */
public class CrashWriterImpl extends AsyncFileWriterImpl<CrashInfo> {

    public CrashWriterImpl(Context context, FileGetter fileGetter) {
        super(context, fileGetter);
    }

    @Override
    protected void doWrite(FileWriter writer, CrashInfo content) throws IOException {
        writer.append(content.toString());
    }
}
